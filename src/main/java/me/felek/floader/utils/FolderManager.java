package me.felek.floader.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import me.felek.floader.FLoader;
import me.felek.floader.api.Mod;
import me.felek.floader.mod.ModEntry;
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

    public static FileHandle getModFile(String path) {
        String internalPath = "assets/" + path;
        FileHandle handle = Gdx.files.classpath(internalPath);

        if (handle.exists()) {
            return handle;
        }

        return null;
    }

    public static String getAssetPath(String path) {
        for (ModEntry mod : ModManager.LOADED_MODS) {
            File file = new File("fmods/" + mod.id + "/assets/" + path);
            if (file.exists()) return file.getAbsolutePath();
        }
        return null;
    }
}
