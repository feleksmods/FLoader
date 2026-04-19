package me.felek.floader;

import javassist.*;
import me.felek.floader.injection.AoCGameInject;
import me.felek.floader.injection.Injected;
import me.felek.floader.lua.LuaManager;
import me.felek.floader.lua.eventSystem.EventBus;
import me.felek.floader.mod.ModManager;
import me.felek.floader.utils.ExitCode;
import me.felek.floader.utils.FolderManager;
import me.felek.floader.utils.KeybindManager;
import me.felek.floader.utils.RegistryManager;
import me.felek.floader.utils.annos.Unsafe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

import me.felek.floader.injection.injs.*;

public class FLoader {
    public static final String VERSION = "Alpha 1.0";
    public static final String NAME = "FLoader";//skids aren't allowed!
    public static final String FULL_NAME = NAME + " " + VERSION;

    public static final Logger LOGGER = LogManager.getLogger(FLoader.class);

    private static Map<String, Injected> injections = new HashMap<>();

    public static void premain(String agentArgs, Instrumentation inst) {
        if (inst == null) {
            ExitCode.CORE_AGENT_ERROR.throwFatalError("Instrumentation instance is null.");
        }

        registerInjections();

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                String clname = className.replace("/", ".");
                if (injections.containsKey(clname)) {
                    try {
                        ClassPool pool = ClassPool.getDefault();
                        pool.appendClassPath(new LoaderClassPath(loader));
                        pool.insertClassPath(new ByteArrayClassPath(clname, classfileBuffer));

                        Injected injector = injections.get(clname);
                        CtClass cc = pool.get(clname);

                        injector.inject(pool, clname);

                        LOGGER.info("Injected logic to: " + clname);
                        return cc.toBytecode();
                    } catch (Exception e) {
                        ExitCode.CORE_TRANSFORMER_ERROR.throwFatalError("Class: " + clname + " | Exception: " + e.getMessage());
                    }
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
        if (LuaManager.GLOBALS != null) return;

        LOGGER.info("Initializing lua execution service");
        LuaManager.init();
        LOGGER.info("Lua execution service initialized.");
        LOGGER.info("Initializing ModManager...");
        ModManager.init();
        LOGGER.info("ModManager initialized.");

        LOGGER.info("Loading FLoader baseMod");
        if (!ModManager.loadBaseMod()) {
            LOGGER.fatal("FLoader FAILED TO START: BaseMod is missing!");
            LOGGER.fatal("Check your JAR file integrity.");
            System.exit(-1);
        }
        LOGGER.info("Base mod successfully loaded.");

        LOGGER.info("Loading mods...");
        ModManager.loadMods();
        LOGGER.info("Mods loaded.");
    }

    @Unsafe
    public static void reload() {
        LOGGER.info("Reloading FLoader...");
        EventBus.clear();
        RegistryManager.clear();
        LuaManager.init();
        ModManager.init();
        KeybindManager.clear();

        LOGGER.info("Loading BaseMod during reload...");
        if (ModManager.loadBaseMod()) {
            LOGGER.info("Loading external mods during reload...");
            ModManager.loadMods();
        }

        LOGGER.info("FLoader reloaded successfully!");
    }
}
