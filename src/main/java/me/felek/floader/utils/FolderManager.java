package me.felek.floader.utils;

import me.felek.floader.FLoader;
import me.felek.floader.mod.Mod;
import me.felek.floader.mod.ModManager;

import java.io.File;

public class FolderManager {
    public static File MODS_DIR = new File("fmods/");

    public static void checkAndCreate() {
        if (!MODS_DIR.exists()) {
            FLoader.LOGGER.warn("Mods folder does not exist. Creating...");
            if (!MODS_DIR.mkdirs()) {
                ExitCode.CORE_FOLDER_ERROR.throwFatalError("Failed to create directory: " + MODS_DIR.getAbsolutePath());
            }
            FLoader.LOGGER.warn("Mods folder created.");
        }
    }

    public static String getAssetPath(String path) {
        for (Mod mod : ModManager.LOADED_MODS) {
            File file = new File("fmods/" + mod.name + "/assets/" + path);
            if (file.exists()) return file.getAbsolutePath();
        }
        return null;
    }
}
