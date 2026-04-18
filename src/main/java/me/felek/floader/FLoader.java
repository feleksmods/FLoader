package me.felek.floader;

import age.of.civilizations2.jakowski.lukasz.CFG;
import javassist.*;
import javassist.expr.ExprEditor;
import me.felek.floader.lua.LuaManager;
import me.felek.floader.mod.ModManager;
import me.felek.floader.utils.FolderManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class FLoader {
    public static final String VERSION = "Alpha 1.0";
    public static final String NAME = "FLoader";//skids aren't allowed!
    public static final String FULL_NAME = NAME + " " + VERSION;

    public static final Logger LOGGER = LogManager.getLogger(FLoader.class);

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                String clname = className.replace("/", ".");
                try {
                    //TODO: Add nice structure for injections
                    if (clname.equals("aoc.kingdoms.lukasz.jakowski.desktop.DesktopLauncher")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);

                        CtMethod m = cc.getDeclaredMethod("main");
                        m.insertBefore(" { me.felek.floader.FLoader.init(); System.out.println(\"FLoader injected into AoH2: DE!\");} ");//INJECTION
                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.GameAction")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);

                        CtMethod m = cc.getDeclaredMethod("startNewTurn_End");
                        m.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onTurnEnd\"); }");

                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.GameCalendar")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);

                        CtMethod mNextDays = cc.getDeclaredMethod("nextDays");
                        mNextDays.insertBefore("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onDayChange\", ($w)$1); }");

                        mNextDays.instrument(new ExprEditor() {
                            public void edit(javassist.expr.FieldAccess f) throws javassist.CannotCompileException {
                                if (f.getFieldName().equals("currMonth") && f.isWriter()) {
                                    f.replace("{ $proceed($$); me.felek.floader.lua.eventSystem.EventBus.call(\"onMonthChange\", ($w)age.of.civilizations2.jakowski.lukasz.GameCalendar.currMonth); }");
                                }
                            }
                        });

                        return cc.toBytecode();
                    } else if(clname.equals("age.of.civilizations2.jakowski.lukasz.War_GameData")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);

                        CtConstructor[] constructors = cc.getDeclaredConstructors();
                        for (CtConstructor constructor : constructors) {
                            if (constructor.getSignature().equals("(II)V")) {
                                constructor.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onWarDeclared\", ($w)$1, ($w)$2); }");
                            }
                        }

                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.CFG")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);
                        CtMethod m = cc.getDeclaredMethod("createUnionCivs");
                        m.insertBefore("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onUnionFormed\", ($w)$1, ($w)$2); }");
                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.Alliance")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);
                        CtMethod m = cc.getDeclaredMethod("setAllianceName");
                        m.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onAllianceFormed\", $1); }");
                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.Civilizations.PeaceT.PeaceTreaty_Data")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);
                        CtMethod m = cc.getDeclaredMethod("preparePeaceTreatyToSend");
                        m.insertBefore("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onPeaceSigned\", ($w)$1); }");
                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.CreateVassal_Data")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);
                        CtMethod m = cc.getDeclaredMethod("setCivTag");
                        m.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onVassalCreated\", $1); }");
                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.Civilizations.Construction.BuildingsConstruction")) {
                        ClassPool pool = ClassPool.getDefault();
                        CtClass cc = pool.get(clname);
                        CtMethod m = cc.getDeclaredMethod("onConstructedRun");
                        m.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onBuildingConstructed\", ($w)$1, ($w)this.iProviID, this.constructionType.toString()); }");
                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.Menus.CreateScenarios.Event_Outcome_BuildingDestroy")) {
                        ClassPool pool = ClassPool.getDefault();
                        CtClass cc = pool.get(clname);
                        CtMethod m = cc.getDeclaredMethod("outcomeAction");
                        m.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onBuildingDestroyed\", ($w)this.iCivID, ($w)this.buildingID); }");
                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.CivInvest")) {
                        ClassPool pool = ClassPool.getDefault();
                        CtClass cc = pool.get(clname);
                        CtConstructor cons = cc.getDeclaredConstructors()[0];
                        cons.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onInvestmentStart\", ($w)$1, ($w)$2, ($w)$3, ($w)$4); }");
                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.Menus.ActionInfo.Menu_InGame_ActionInfo_TreasuryIsEmpty")) {
                        ClassPool pool = ClassPool.getDefault();
                        CtClass cc = pool.get(clname);
                        CtConstructor cons = cc.getDeclaredConstructors()[0];
                        cons.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onTreasuryEmpty\"); }");
                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.Civilizations.Loan_GameData")) {
                        ClassPool pool = ClassPool.getDefault();
                        CtClass cc = pool.get(clname);
                        CtConstructor cons = cc.getDeclaredConstructors()[0];
                        cons.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onLoanTaken\", ($w)$1, ($w)$2); }");
                        return cc.toBytecode();
                    } else if (clname.equals("aoc.kingdoms.lukasz.jakowski.AA_Game")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);
                        CtMethod m = cc.getDeclaredMethod("create");

                        m.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onGameInitialization\"); }");
                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.Console.Commands")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);

                        CtMethod m = cc.getDeclaredMethod("execute");
                        m.insertBefore("{ if (me.felek.floader.utils.RegistryManager.executeCommand($1)) return; }");

                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.Menus.Menu_InitGame")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);

                        CtMethod m = cc.getDeclaredMethod("loadBackground");
                        m.insertAfter("{" +
                                "age.of.civilizations2.jakowski.lukasz.Image customImg = me.felek.floader.utils.RegistryManager.getRandomCustomScreen();" +
                                "if (customImg != null) {" +
                                "   background = customImg;" +
                                "   backgroundWidth = age.of.civilizations2.jakowski.lukasz.CFG.GAMEWIDTH;" +
                                "   backgroundHeight = age.of.civilizations2.jakowski.lukasz.CFG.GAMEHEIGHT;" +
                                "}" +
                                "}");

                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.LangManager")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);

                        CtMethod mGetLOA = cc.getDeclaredMethod("getLOA");
                        mGetLOA.insertBefore("{" +
                                "try {" +
                                "   int tipId = Integer.parseInt($1);" +
                                "   if (tipId >= (this.iLNOT - me.felek.floader.utils.RegistryManager.getTipsCount())) {" +
                                "       int customIdx = tipId - (this.iLNOT - me.felek.floader.utils.RegistryManager.getTipsCount());" +
                                "       return me.felek.floader.utils.RegistryManager.getTip(customIdx);" +
                                "   }" +
                                "} catch (Exception e) {}" +
                                "}");

                        CtMethod mInit = cc.getDeclaredMethod("initLoadingBundle");
                        mInit.insertAfter("{ this.iLNOT += me.felek.floader.utils.RegistryManager.getTipsCount(); }");

                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);

                        CtMethod mLoad = cc.getDeclaredMethod("loadEventIMG");
                        mLoad.insertBefore("{" +
                                "String pictureKey = age.of.civilizations2.jakowski.lukasz.CFG.eventsManager.getEvent(EVENT_ID).getEventPicture();" +
                                "if (me.felek.floader.utils.RegistryManager.resources.containsKey(pictureKey)) {" +
                                "   eventsIMGs.clear();" +
                                "   eventsIMGs.add((age.of.civilizations2.jakowski.lukasz.Image)me.felek.floader.utils.RegistryManager.resources.get(pictureKey));" +
                                "   ANIMATION_IMG_ID = 0;" +
                                "   ANIMATION_TIME = System.currentTimeMillis();" +
                                "   return;" +
                                "}" +
                                "}");

                        CtMethod m = cc.getDeclaredMethod("setVisibleM");
                        m.insertBefore("{" +
                                "if (!$1) {" + //if (visilbe==false)
                                "   String pictureKey = age.of.civilizations2.jakowski.lukasz.CFG.eventsManager.getEvent(EVENT_ID).getEventPicture();" +
                                "   if (me.felek.floader.utils.RegistryManager.resources.containsKey(pictureKey)) {" +
                                "       eventsIMGs.clear();" +
                                "   }" +
                                "}" +
                                "}");

                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event2")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);

                        CtMethod m = cc.getDeclaredMethod("loadEventIMG");
                        m.insertBefore("{" +
                                "String pictureKey = age.of.civilizations2.jakowski.lukasz.CFG.eventsManager.getEvent(age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event.EVENT_ID).getEventPicture();" +
                                "if (me.felek.floader.utils.RegistryManager.resources.containsKey(pictureKey)) {" +
                                "   age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event.eventsIMGs.clear();" +
                                "   age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event.eventsIMGs.add((age.of.civilizations2.jakowski.lukasz.Image)me.felek.floader.utils.RegistryManager.resources.get(pictureKey));" +
                                "   age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event.ANIMATION_IMG_ID = 0;" +
                                "   age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event.ANIMATION_TIME = System.currentTimeMillis();" +
                                "   return;" +
                                "}" +
                                "}");

                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.ReligionManager")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);

                        cc.getDeclaredField("lReligions").setModifiers(java.lang.reflect.Modifier.PUBLIC);
                        cc.getDeclaredField("iReligionsSize").setModifiers(java.lang.reflect.Modifier.PUBLIC);

                        CtMethod mLoad = cc.getDeclaredMethod("loadReligions");
                        mLoad.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onReligionsInit\"); }");

                        CtMethod mGet = cc.getDeclaredMethod("getReligion");
                        mGet.insertBefore("if ($1 >= this.lReligions.size() || $1 < 0) return (age.of.civilizations2.jakowski.lukasz.ReligionManager$Religion)this.lReligions.get(0);");

                        return cc.toBytecode();
                    } else if (clname.equals("age.of.civilizations2.jakowski.lukasz.IdeologiesManager")) {
                        ClassPool pool = ClassPool.getDefault();
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        CtClass cc = pool.get(clname);

                        cc.getDeclaredField("lIdeologies").setModifiers(java.lang.reflect.Modifier.PUBLIC);

                        CtMethod mLoad = cc.getDeclaredMethod("loadIdeologies");
                        mLoad.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onIdeologiesInit\"); }");

                        return cc.toBytecode();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        });
    }

    public static void init() {
        LOGGER.info("Starting FLoader");
        LOGGER.info("Injecting agent");
        LOGGER.info("Checking folders...");
        FolderManager.checkAndCreate();
        LOGGER.info("Folders checked.");
        LOGGER.info("Initializing lua execution service");
        LuaManager.init();
        LOGGER.info("Lua execution service initialized.");
        LOGGER.info("Initializing ModManager...");
        ModManager.init();
        LOGGER.info("ModManager initialized.");
        LOGGER.info("Loading mods...");
        ModManager.loadMods();
        LOGGER.info("Mods loaded.");
    }
}
