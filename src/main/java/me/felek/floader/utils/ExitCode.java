package me.felek.floader.utils;

import me.felek.floader.FLoader;

public enum ExitCode {//TODO: Implement all codes and add some new codes
    CORE_FOLDER_ERROR(-11, "Could not create mods directory."),
    CORE_TRANSFORMER_ERROR(-12, "Javassist failed to transform a critical game class."),
    CORE_REFLECTION_ERROR(-13, "Reflection failed to access a private field in AoH2."),
    CORE_AGENT_ERROR(-14, "Java Agent could not be initialized."),

    LUA_INIT_ERROR(-21, "Failed to initialize Lua Globals or JsePlatform."),
    LUA_BASE_MOD_MISSING(-22, "Internal BaseMod script not found in JAR."),
    LUA_BASE_MOD_CRASH(-23, "Critical error in BaseMod logic execution."),
    LUA_BINDING_ERROR(-24, "Failed to bind Java method to Lua space."),
    LUA_RUNTIME_ERROR(-25, "Mod logic execution failed during runtime."),

    MOD_CONFIG_CORRUPT(-31, "config.json is invalid or missing in a mod folder."),
    MOD_CYCLIC_DEPENDENCY(-32, "Circular dependency detected between mods."),
    MOD_MISSING_DEPENDENCY(-33, "A required dependency for a mod was not found."),
    MOD_MAIN_SCRIPT_MISSING(-34, "The main Lua file specified in config.json is missing."),

    WORLD_CIV_INVALID_ID(-41, "Attempted to access a Civilization with an out-of-bounds ID."),//TODO: add more codes
    WORLD_CIV_NULL(-42, "API: Game returned a null Civilization object."),
    WORLD_CIV_DATA_CORRUPT(-43, "Civilization GameData (civGD) is null or corrupted."),
    WORLD_CIV_GOLD_ERROR(-44, "Invalid gold/money operation (potential overflow or NaN)."),

    WORLD_PROV_INVALID_ID(-51, "Attempted to access a Province with an out-of-bounds ID."),
    WORLD_PROV_NULL(-52, "Province object is null."),
    WORLD_PROV_OWNER_ERROR(-53, "Failed to set/get province owner (Invalid Civ ID)."),

    REG_IMAGE_LOAD_FAILED(-61, "Failed to load image texture via LibGDX."),
    REG_RESOURCE_DUPLICATE(-62, "Resource key collision detected."),
    REG_COMMAND_ERROR(-63, "Failed to register or execute a custom console command."),

    UNSAFE_RELOAD_ERROR(-91, "FLoader reload failed. Engine state is inconsistent."),
    UNSAFE_CFG_ACCESS_ERROR(-92, "Direct access to CFG fields failed.")
    ;

    private int id;
    private String name;

    ExitCode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void throwFatalError(String details) {
        FLoader.LOGGER.fatal("=".repeat(60));
        FLoader.LOGGER.fatal("FLoader STOPPED!");
        FLoader.LOGGER.fatal("Reason: " + name);
        FLoader.LOGGER.fatal("Details: " + details);
        FLoader.LOGGER.fatal("Exit code: " + id);
        FLoader.LOGGER.fatal("=".repeat(60));
        System.exit(id);
    }
}
