package me.felek.floader.mod;

import com.google.gson.Gson;
import me.felek.floader.FLoader;
import me.felek.floader.lua.LuaManager;
import me.felek.floader.utils.FolderManager;
import me.felek.floader.utils.IOLib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModManager {
    public static List<Mod> LOADED_MODS;
    private static final Gson gson = new Gson();

    public static void init() {
        LOADED_MODS = new ArrayList<>();
    }

    public static Mod getModByName(String name) {
         for (Mod mod : LOADED_MODS) {
             if (mod.name.equals(name)) {
                 return mod;
             }
         }

         return null;
    }

    public static boolean loadBaseMod() {
        FLoader.LOGGER.info("Loading internal BaseMod");
        String script = IOLib.readResourceText("/floader/scripts/script.lua");

        if (script == null || script.isEmpty()) {
            FLoader.LOGGER.error("CRITICAL ERROR: Internal BaseMod not found in JAR resources!");
            return false;
        }

        try {
            LuaManager.GLOBALS.load(script).call();
            FLoader.LOGGER.info("BaseMod initialized.");
            return true;
        } catch (Exception e) {
            FLoader.LOGGER.error("CRITICAL ERROR: Failed to initialize BaseMod: " + e.getMessage());
            return false;
        }
    }

    public static void loadMods() {
        for (File modDir : Objects.requireNonNull(FolderManager.MODS_DIR.listFiles())) {
            parseMod(modDir);
        }

        FLoader.LOGGER.info("Loaded " + LOADED_MODS.size() + " mods.");
        FLoader.LOGGER.info("Starting initialize_mod function...");

        for (Mod mod : LOADED_MODS) {
            File script = new File(FolderManager.MODS_DIR, mod.name + "/scripts/" + mod.mainFile);
            if (!script.exists()) {
                FLoader.LOGGER.error("Couldn't initialize_mod() call in " + mod.name + " because " + mod.mainFile + " is missing!");
                continue;
            }

            try {
                FLoader.LOGGER.info("Initializing " + mod.name + "...");
                LuaManager.GLOBALS.loadfile(script.getAbsolutePath()).call();
            } catch (Exception e) {
                FLoader.LOGGER.error("Couldn't initialize_mod() call in " + mod.name + " because " + e.getMessage());
            }
        }
    }

    private static void parseMod(File modDir) {
        File cfgFile = new File(modDir, "config/config.json");

        if (!cfgFile.exists()) {
            FLoader.LOGGER.error("Couldn't load mod " + modDir.getName() + " because config.json is missing!");
            return;
        }

        try {
            String cfgContent = IOLib.readAllText(cfgFile);

            Mod mod = gson.fromJson(cfgContent, Mod.class);
            if (mod == null || cfgContent == null) {
                FLoader.LOGGER.error("Couldn't load mod " + modDir.getName() + " because config.json is invalid!");
                return;
            }

            FLoader.LOGGER.info("Loading mod: " + mod.name + " v" + mod.version + " by " + mod.author);

            LOADED_MODS.add(mod);
        } catch (Exception exc) {
            FLoader.LOGGER.error("Couldn't load mod " + modDir.getName() + " because config.json is invalid!");
        }
    }
}
