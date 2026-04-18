package me.felek.floader.mod;

import java.util.ArrayList;
import java.util.List;

public class Mod {
    public String name;
    public String version;
    public String description;
    public String author;
    public String mainFile;
    public List<String> dependencies = new ArrayList<>();
}
