package me.felek.floader.api.game;

import age.of.civilizations2.jakowski.lukasz.Image;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface IRegistry {
    void registerStation(String name, List<String> tracks);
    void clear();
    void registerCommand(String commandName, Consumer<String[]> func);
    void registerLoadingScreen(String resourceKey);
    void registerResource(String key, Image img);
    Image getRandomCustomScreen();
    boolean executeCommand(String full);
    void registerLoadingTip(String tip);
    int getTipsCount();
    String getTip(int index);

    boolean containsResource(String key);
    age.of.civilizations2.jakowski.lukasz.Image getResource(String key);
    Map getCustomStations();
    Image loadImage(String modId, String path);
}
