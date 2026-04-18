package me.felek.floader.utils;

import me.felek.floader.FLoader;

import java.io.File;

public class FolderManager {
    public static File MODS_DIR = new File("fmods/");

    public static void checkAndCreate() {
        if (!MODS_DIR.exists()) {
            FLoader.LOGGER.warn("Mods folder does not exist. Creating...");
            MODS_DIR.mkdir();
            FLoader.LOGGER.warn("Mods folder created.");
        }
    }
}
