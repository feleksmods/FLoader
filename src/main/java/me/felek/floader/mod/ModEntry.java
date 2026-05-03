package me.felek.floader.mod;

import me.felek.floader.api.IMod;
import me.felek.floader.api.Mod;

import java.io.File;

public class ModEntry {
    public final String id;
    public final String version;
    public final String[] dependencies;
    public final IMod instance;

    public ModEntry(Mod modAnnotation, IMod instance) {
        this.id = modAnnotation.id();
        this.version = modAnnotation.version();
        this.dependencies = modAnnotation.dependencies();
        this.instance = instance;
    }
}
