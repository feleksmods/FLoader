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
import java.util.*;

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
        File[] files = FolderManager.MODS_DIR.listFiles();
        if (files==null) return;

        List<Mod> raw = new ArrayList<>();

        for (File modDir : files) {
            if (modDir.isDirectory()) {
                Mod mod = parseModCfg(modDir);
                if (mod != null) raw.add(mod);
            }
        }

        LOADED_MODS = sortByDependencies(raw);
        FLoader.LOGGER.info("Resolved load order for " + LOADED_MODS.size() + " mods");
        for (Mod mod : LOADED_MODS) {
            File script = new File(FolderManager.MODS_DIR, mod.name + "/scripts/" + mod.mainFile);
            if (!script.exists()) {
                FLoader.LOGGER.error("Couldn't find main file for " + mod.name);
                continue;
            }

            try {
                FLoader.LOGGER.info("Initializing " + mod.name + "...");
                LuaManager.GLOBALS.loadfile(script.getAbsolutePath()).call();
            } catch (Exception e) {
                FLoader.LOGGER.error("Error in " + mod.name + ": " + e.getMessage());
            }
        }
    }

    private static Mod parseModCfg(File dir) {
        File cfg = new File(dir, "config/config.json");

        try {
            String cfgContent = IOLib.readAllText(cfg);
            Mod mod = gson.fromJson(cfgContent, Mod.class);
            if (mod != null) return mod;
        } catch (Exception exception) {
            FLoader.LOGGER.error("Invalid config: " + dir.getName());
        }

        return null;
    }

    private static List<Mod> sortByDependencies(List<Mod> mods) {
        List<Mod> sorted = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Set<String> loading = new HashSet<>();
        Map<String, Mod> modMap = new HashMap<>();

        for (Mod m : mods) modMap.put(m.name, m);
        for (Mod m : mods) {
            if (!visited.contains(m.name)) {
                visit(m, modMap, visited, loading, sorted);
            }
        }
        return sorted;
    }

    private static void visit(Mod mod, Map<String, Mod> allMods, Set<String> visited, Set<String> loading, List<Mod> sorted) {
        if (loading.contains(mod.name)) {
            FLoader.LOGGER.error("Cyclic dependency found: " + mod.name);
            return;
        }

        if (!visited.contains(mod.name)) {
            loading.add(mod.name);
            if (mod.dependencies != null) {
                for (String depName : mod.dependencies) {
                    Mod dep = allMods.get(depName);
                    if (dep != null) {
                        visit(dep, allMods, visited, loading, sorted);
                    } else {
                        FLoader.LOGGER.warn("Mod '" + mod.name + "' misses optional dependency: " + depName);
                    }
                }
            }

            loading.remove(mod.name);
            visited.add(mod.name);
            sorted.add(mod);
        }
    }
}
