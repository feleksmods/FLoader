package me.felek.floader.mod;

import me.felek.floader.FLoader;
import me.felek.floader.api.IMod;
import me.felek.floader.api.Mod;
import me.felek.floader.utils.FolderManager;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Collectors;

public class ModManager {
    public static List<ModEntry> LOADED_MODS;

    public static void init() {
        LOADED_MODS = new ArrayList<>();
    }

    public static void loadAndInitializeMods() {
        FLoader.LOGGER.info("Searching for mods...");
        List<ModEntry> foundMods = new ArrayList<>();//TODO: split into method
        File[] files = FolderManager.MODS_DIR.listFiles();
        if (files != null) {
            for (File modFile : files) {
                if (modFile.isFile() && modFile.getName().endsWith(".jar")) {
                    try {
                        URLClassLoader classLoader = new URLClassLoader(new URL[]{modFile.toURI().toURL()}, ModManager.class.getClassLoader());
                        Reflections reflections = new Reflections(new ConfigurationBuilder()
                                .setUrls(modFile.toURI().toURL())
                                .addClassLoaders(classLoader)
                                .setScanners(Scanners.TypesAnnotated));

                        Set<Class<?>> modClasses = reflections.getTypesAnnotatedWith(Mod.class);

                        for (Class<?> clazz : modClasses) {
                            if (IMod.class.isAssignableFrom(clazz)) {
                                Mod anno = clazz.getAnnotation(Mod.class);
                                IMod instance = (IMod) clazz.getDeclaredConstructor().newInstance();
                                foundMods.add(new ModEntry(anno, instance));
                                FLoader.LOGGER.info("Detected mod: " + anno.id());
                            } else {
                                FLoader.LOGGER.error("Class selected as @Mod but not implementing IMod.");
                            }
                        }
                    } catch (Exception exc) {
                        FLoader.LOGGER.error("Error while reading .jar file!\n" + exc.toString());
                    }
                }
            }
        }

        if (foundMods.isEmpty()) {
            FLoader.LOGGER.info("No mods found.");
            return;
        }

        FLoader.LOGGER.info("Sorting mods and dependencies...");
        List<ModEntry> sortedMods = sortByDependencies(foundMods);

        LOADED_MODS.addAll(sortedMods);

        FLoader.LOGGER.info("Initializing mods...");
        for (ModEntry entry : LOADED_MODS) {
            try {
                FLoader.LOGGER.info("-> Initializing mod " + entry.id + " v." + entry.version);
                entry.instance.onPreInitialization();
            } catch (Exception exc) {
                FLoader.LOGGER.error("Critical error while loading mod.");//TODO: error code here!
            }
        }
        for (ModEntry entry : LOADED_MODS) {
            try {
                FLoader.LOGGER.info("-> Enabling mod " + entry.id + " v." + entry.version);
                entry.instance.onEnable();
            } catch (Exception exc) {
                FLoader.LOGGER.error("Critical error while enabling mod.");//TODO: error code here!
            }
        }

        FLoader.LOGGER.info("Mods initialized.");
    }

    private static List<ModEntry> sortByDependencies(List<ModEntry> mods) {
        List<ModEntry> sorted = new ArrayList<>();
        Map<String, ModEntry> modMap = mods.stream().collect(Collectors.toMap(m -> m.id, m -> m));
        Set<String> visited = new HashSet<>();
        Set<String> visiting = new HashSet<>();

        for (ModEntry mod : mods) {
            if (!visited.contains(mod.id)) {
                visit(mod, modMap, visited, visiting, sorted);
            }
        }
        return sorted;
    }

    private static void visit(ModEntry mod, Map<String, ModEntry> modMap, Set<String> visited, Set<String> visiting, List<ModEntry> sorted) {
        if (visiting.contains(mod.id)) {
            throw new RuntimeException("Detected cyclic depending: " + mod.id);
        }
        if (visited.contains(mod.id)) {
            return;
        }

        visiting.add(mod.id);

        for (String depId : mod.dependencies) {
            ModEntry dependency = modMap.get(depId);
            if (dependency != null) {
                visit(dependency, modMap, visited, visiting, sorted);
            } else {
                throw new RuntimeException("Mod '" + mod.id + "' depending '" + depId + "', which not found!");
            }
        }

        visiting.remove(mod.id);
        visited.add(mod.id);
        sorted.add(mod);
    }
}
