package me.felek.floader;

import javassist.*;
import me.felek.floader.api.mixin.At;
import me.felek.floader.api.mixin.Inject;
import me.felek.floader.injection.injs.AoCGameInject;
import me.felek.floader.injection.injs.GameManagerInject;
import me.felek.floader.injection.Injection;
import me.felek.floader.mixin.MixinData;
import me.felek.floader.mixin.MixinRegistry;
import me.felek.floader.mod.ModManager;
import me.felek.floader.utils.ExitCode;
import me.felek.floader.utils.FolderManager;
import me.felek.floader.utils.RegistryManager;
import me.felek.floader.utils.annos.Unsafe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

import me.felek.floader.injection.injs.*;

public class FLoader {
    public static final String VERSION = "0.5D";
    public static final String NAME = "FLoader";//skids aren't allowed!
    public static final String FULL_NAME = NAME + " " + VERSION;

    public static final Logger LOGGER = LogManager.getLogger(FLoader.class);

    private static Map<String, Injection> injections = new HashMap<>();

    public static void premain(String agentArgs, Instrumentation inst) {
        if (inst == null) {
            ExitCode.CORE_AGENT_ERROR.throwFatalError("Instrumentation instance is null.");
        }

        File[] files = new File("fmods/").listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".jar")) {
                    try { inst.appendToSystemClassLoaderSearch(new java.util.jar.JarFile(file)); }
                    catch (Exception e) { LOGGER.error(e); }
                }
            }
        }
        ModManager.discoverMods();

        registerInjections();

        me.felek.floader.api.FLoader.registryManager = new RegistryManager();
        me.felek.floader.api.FLoader.game = new me.felek.floader.utils.impl.GameImpl();
        me.felek.floader.api.FLoader.settings = new me.felek.floader.utils.impl.SettingsImpl();
        me.felek.floader.api.FLoader.steam = new me.felek.floader.utils.impl.SteamImpl();
        me.felek.floader.api.FLoader.utils = new me.felek.floader.utils.impl.UtilsImpl();

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                String clname = className.replace("/", ".");

                boolean modified = false;
                ClassPool pool = ClassPool.getDefault();
                CtClass cc = null;

                try {
                    if (MixinRegistry.hasMixinsFor(clname)) {
                        cc = pool.get(clname);
                        pool.appendClassPath(new LoaderClassPath(loader));

                        for (MixinData mixin : MixinRegistry.getMixinsFor(clname)) {
                            for (Method m : mixin.methods) {
                                Inject injectAnno = m.getAnnotation(Inject.class);
                                CtMethod targetMethod = cc.getDeclaredMethod(injectAnno.method());
                                String code = mixin.mixinClass.getName() + "." + m.getName() + "($$);";

                                if (injectAnno.at() == At.HEAD) targetMethod.insertBefore(code);
                                else targetMethod.insertAfter(code);
                            }
                        }
                        modified = true;
                    }

                    if (injections.containsKey(clname)) {
                        if (cc == null) {
                            cc = pool.get(clname);
                            pool.appendClassPath(new LoaderClassPath(loader));
                            pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));
                        }

                        injections.get(clname).inject(pool, clname);
                        LOGGER.info("Injected logic to: " + clname);
                        modified = true;
                    }

                    if (modified && cc != null) {
                        return cc.toBytecode();
                    }

                } catch (Exception e) {
                    LOGGER.error("Error transforming class " + clname, e);
                }

                return null;
            }
        });
    }

    private static void registerInjections() {
        injections.put("aoc.kingdoms.lukasz.jakowski.desktop.DesktopLauncher", new DesktopLauncherInject());
        injections.put("aoc.kingdoms.lukasz.jakowski.AA_Game", new AAGameInject());

        injections.put("age.of.civilizations2.jakowski.lukasz.GameAction", new GameActionInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.GameCalendar", new GameCalendarInject());

        injections.put("age.of.civilizations2.jakowski.lukasz.War_GameData", new WarGameDataInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.CFG", new CFGInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.Alliance", new AllianceInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.CreateVassal_Data", new CreateVassalDataInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.Civilizations.PeaceT.PeaceTreaty_Data", new PeaceTreatyDataInject());

        injections.put("age.of.civilizations2.jakowski.lukasz.Civilizations.Construction.BuildingsConstruction", new BuildingsConstructionInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.CivInvest", new CivInvestInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.Civilizations.Loan_GameData", new LoanGameDataInject());

        injections.put("age.of.civilizations2.jakowski.lukasz.ReligionManager", new ReligionManagerInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.IdeologiesManager", new IdeologiesManagerInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.LangManager", new LangManagerInject());

        injections.put("age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event", new MenuInGameEventInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event2", new MenuInGameEvent2Inject());
        injections.put("age.of.civilizations2.jakowski.lukasz.Menus.Menu_InitGame", new MenuInitGameInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.Menus.CreateScenarios.Event_Outcome_BuildingDestroy", new EventOutcomeBuildDestroy());
        injections.put("age.of.civilizations2.jakowski.lukasz.Menus.ActionInfo.Menu_InGame_ActionInfo_TreasuryIsEmpty", new MenuInGameActionInfoTreasureIsEmptyInject());

        injections.put("age.of.civilizations2.jakowski.lukasz.Console.Commands", new CommandsInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.AoCGame", new AoCGameInject());

        injections.put("age.of.civilizations2.jakowski.lukasz.TouchManager", new TouchInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.Civilization", new CivilizationInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.Province", new ProvinceInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.GameManager", new GameManagerInject());

        injections.put("age.of.civilizations2.jakowski.lukasz.Files.FileManager", new FileManagerInject());
        injections.put("age.of.civilizations2.jakowski.lukasz.SFXManager", new SFXManagerInject());
    }

    public static void init() {
        LOGGER.info("Starting FLoader pre-init");
        LOGGER.info("Injecting agent");
        LOGGER.info("Checking folders...");
        FolderManager.checkAndCreate();
        LOGGER.info("Folders checked.");

        LOGGER.info("FLoader pre-init done.");
    }

    public static void initModdingTools() {
//        LOGGER.info("Initializing ModManager...");
//        ModManager.init();
//        LOGGER.info("ModManager initialized.");

        LOGGER.info("Loading mods...");
        ModManager.initializeMods();
        LOGGER.info("Mods loaded.");
    }

    @Unsafe
    public static void reload() {//TODO: port
//        LOGGER.info("Reloading FLoader...");
//        Eve.clear();
//        RegistryManager.clear();
//        LuaManager.init();
//        ModManager.init();
//        KeybindManager.clear();
//
//        LOGGER.info("Loading BaseMod during reload...");
//        if (ModManager.loadBaseMod()) {
//            LOGGER.info("Loading external mods during reload...");
//            ModManager.loadAndInitializeMods();
//        }
//
//        LOGGER.info("FLoader reloaded successfully!");
    }
}
