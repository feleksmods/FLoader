package me.felek.floader.utils;

import age.of.civilizations2.jakowski.lukasz.Image;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.*;

public class RegistryManager {
    public static List<String> customLoadingTips = new ArrayList<>();
    public static final Map<String, LuaFunction> commands = new HashMap<>();
    public static final Map<String, Image> resources = new HashMap<>();
    public static final List<String> customLoadingScreens = new ArrayList<>();

    public static void registerCommand(String commandName, LuaFunction func) {
        commands.put(commandName, func);
    }

    public static void registerLoadingScreen(String resourceKey) {
        if (resources.containsKey(resourceKey)) {
            customLoadingScreens.add(resourceKey);
        }
    }

    public static void registerResource(String key, Image img) {
        resources.put(key, img);
    }

    public static Image getRandomCustomScreen() {
        if (customLoadingScreens.isEmpty()) return null;
        String key = customLoadingScreens.get(new Random().nextInt(customLoadingScreens.size()));
        return resources.get(key);
    }


    public static boolean executeCommand(String full) {
        String[] splitted = full.split(" ");
        String base = splitted[0].toLowerCase();

        if (commands.containsKey(base)) {
            try {
                LuaTable args = LuaValue.tableOf();
                for (int i = 0; i < splitted.length; i++) {
                    args.set(i,LuaValue.valueOf(splitted[i]));
                }

                commands.get(base).call(args);
                return true;
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }

        return false;
    }

    public static void registerLoadingTip(String tip) {
        customLoadingTips.add(tip);
    }

    public static int getTipsCount() {
        return customLoadingTips.size();
    }

    public static String getTip(int index) {
        if (index >= 0 && index < customLoadingTips.size()) {
            return (String) customLoadingTips.get(index);
        }
        return "FLoader Error: Tip index out of bounds";
    }
}
