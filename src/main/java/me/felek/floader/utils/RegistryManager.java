package me.felek.floader.utils;

import age.of.civilizations2.jakowski.lukasz.Image;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import me.felek.floader.FLoader;
import me.felek.floader.api.game.IRegistry;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

public class RegistryManager implements IRegistry {
    public List<String> customLoadingTips = new ArrayList<>();
    public final Map<String, Consumer<String[]>> commands = new HashMap<>();
    public final Map<String, Image> resources = new HashMap<>();
    public final List<String> customLoadingScreens = new ArrayList<>();
    public Map<String, List<String>> customStations = new HashMap<>();

    public void registerStation(String name, List<String> tracks) {
        customStations.put(name, tracks);
    }

    public void clear() {
        customLoadingTips.clear();
        commands.clear();
        customLoadingScreens.clear();
        FLoader.LOGGER.info("Registry cleared.");
    }

    public void registerCommand(String commandName, Consumer<String[]> func) {
        if (commands.containsKey(commandName.toLowerCase())) {
            ExitCode.REG_COMMAND_ERROR.throwFatalError("Command duplicate: " + commandName);
        }
        commands.put(commandName.toLowerCase(), func);
    }

    public void registerLoadingScreen(String resourceKey) {
        if (resources.containsKey(resourceKey)) {
            customLoadingScreens.add(resourceKey);
        }
    }


    public void registerResource(String key, Image img) {
        if (resources.containsKey(key)) {
            ExitCode.REG_RESOURCE_DUPLICATE.throwFatalError("Resource collision: " + key);
        }
        resources.put(key, img);
    }

    public Image getRandomCustomScreen() {
        if (customLoadingScreens.isEmpty()) return null;
        String key = customLoadingScreens.get(new Random().nextInt(customLoadingScreens.size()));
        return resources.get(key);
    }


    public boolean executeCommand(String full) {
        if (full == null || full.isEmpty()) return false;

        String[] splitted = full.split(" ");
        String base = splitted[0].toLowerCase();

        if (commands.containsKey(base)) {
            try {
                commands.get(base).accept(splitted);
                return true;
            } catch (Exception exc) {
                FLoader.LOGGER.error("Error executing command '" + base + "': " + exc.getMessage());
                exc.printStackTrace();
            }
        }

        return false;
    }

    public void registerLoadingTip(String tip) {
        customLoadingTips.add(tip);
    }

    public int getTipsCount() {
        return customLoadingTips.size();
    }

    public String getTip(int index) {
        if (index >= 0 && index < customLoadingTips.size()) {
            return (String) customLoadingTips.get(index);
        }
        return "FLoader Error: Tip index out of bounds";
    }

    @Override
    public boolean containsResource(String key) {
        return resources.containsKey(key);
    }

    @Override
    public Image getResource(String key) {
        return resources.get(key);
    }

    @Override
    public Map getCustomStations() {
        return customStations;
    }

    @Override
    public Image loadImage(String modId, String path) {
        String key = modId + ":" + path;
        if (resources.containsKey(key)) return resources.get(key);

        FileHandle handle = FolderManager.getModFile(path);
        if (handle != null) {
            try {
                Texture tex = new Texture(handle);
                tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

                Image img = new Image(tex, Texture.TextureFilter.Linear, Texture.TextureWrap.ClampToEdge);
                resources.put(key, img);

                return img;
            } catch (Exception e) {
                FLoader.LOGGER.error("Failed to load image: " + handle.file().getAbsolutePath(), e);
            }
        }

        return null;
    }
}
