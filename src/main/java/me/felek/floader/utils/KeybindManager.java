package me.felek.floader.utils;

import com.badlogic.gdx.Gdx;
import org.luaj.vm2.LuaFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeybindManager {
    public static final Map<Integer, LuaFunction> binds = new ConcurrentHashMap<>();

    public static void registerBind(int keycode, LuaFunction func) {
        binds.put(keycode, func);
    }

    public static void clear() {
        binds.clear();
    }

    public static void update() {
        for (Integer key : binds.keySet()) {
            if (Gdx.input.isKeyJustPressed(key)) {
                binds.get(key).call();
            }
        }
    }
}
