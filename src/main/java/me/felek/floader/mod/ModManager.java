package me.felek.floader.mod;

import me.felek.floader.FLoader;
import me.felek.floader.api.IMod;
import me.felek.floader.api.Mod;
import me.felek.floader.api.mixin.Inject;
import me.felek.floader.api.mixin.Mixin;
import me.felek.floader.mixin.MixinData;
import me.felek.floader.mixin.MixinRegistry;
import me.felek.floader.utils.FolderManager;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Collectors;

public class ModManager {
    public static List<ModEntry> LOADED_MODS = new ArrayList<>();

    public static void init() {
        LOADED_MODS.clear();
        MixinRegistry.clear();
    }

    public static void discoverMods() {
        FLoader.LOGGER.info("Scanning for JAR's and mixins...");
        File[] files = FolderManager.MODS_DIR.listFiles();
        if (files == null) return;

        List<ModEntry> found = new ArrayList<>();

        for (File modFile : files) {
            if (modFile.isFile() && modFile.getName().endsWith(".jar")) {
                try {
                    URL[] urls = {modFile.toURI().toURL()};
                    URLClassLoader modClassLoader = new URLClassLoader(urls, ModManager.class.getClassLoader());

                    Reflections reflections = new Reflections(new ConfigurationBuilder()
                            .setUrls(urls)
                            .addClassLoaders(modClassLoader)
                            .setScanners(Scanners.TypesAnnotated));

                    Set<Class<?>> modClasses = reflections.getTypesAnnotatedWith(Mod.class);
                    for (Class<?> clazz : modClasses) {
                        if (IMod.class.isAssignableFrom(clazz)) {
                            Mod anno = clazz.getAnnotation(Mod.class);
                            IMod instance = (IMod) clazz.getDeclaredConstructor().newInstance();
                            found.add(new ModEntry(anno, instance));
                            FLoader.LOGGER.info("Found mod: " + anno.id() + " v." + anno.version());
                        }
                    }

                    Set<Class<?>> mixinClasses = reflections.getTypesAnnotatedWith(Mixin.class);
                    for (Class<?> clazz : mixinClasses) {
                        Mixin anno = clazz.getAnnotation(Mixin.class);
                        MixinData data = new MixinData(clazz, anno.target());

                        for (Method m : clazz.getDeclaredMethods()) {
                            if (m.isAnnotationPresent(Inject.class)) {
                                data.methods.add(m);
                            }
                        }
                        MixinRegistry.register(data);
                    }
                } catch (Exception exc) {
                    FLoader.LOGGER.error("Failed to scan mod " + modFile.getName(), exc);
                }
            }
        }

        LOADED_MODS.addAll(sortByDependencies(found));
        FLoader.LOGGER.info("Loaded" + LOADED_MODS.size() + "mods");
    }

    public static void initializeMods() {
        FLoader.LOGGER.info("Initializing mods...");
        for (ModEntry entry : LOADED_MODS) {
            try {
                entry.instance.onPreInitialization();
            } catch (Exception e) {
                FLoader.LOGGER.error("Error during pre-init of " + entry.id, e);
            }
        }
        for (ModEntry entry : LOADED_MODS) {
            try {
                entry.instance.onEnable();
            } catch (Exception e) {
                FLoader.LOGGER.error("Error enabling mod " + entry.id, e);
            }
        }
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
