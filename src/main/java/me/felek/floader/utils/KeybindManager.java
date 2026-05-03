package me.felek.floader.utils;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeybindManager {
    public static final Map<Integer, Runnable> binds = new ConcurrentHashMap<>();

    public static void registerBind(int keycode, Runnable func) {
        binds.put(keycode, func);
    }

    public static void clear() {
        binds.clear();
    }

    public static void update() {
        for (Integer key : binds.keySet()) {
            if (Gdx.input.isKeyJustPressed(key)) {
                Runnable action = binds.get(key);
                if (action != null) {
                    try {
                        action.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
