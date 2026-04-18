package me.felek.floader.lua.eventSystem;

import me.felek.floader.FLoader;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventBus {
    private static final Map<String, List<LuaFunction>> listeners = new HashMap<>();

    public static void subscribe(String eventName, LuaFunction handler) {
        listeners.computeIfAbsent(eventName, list -> new LinkedList<>()).add(handler);
    }

    public static void clear() {
        listeners.clear();
        FLoader.LOGGER.info("EventBus cleared.");
    }

    public static void call(String eventName) {
        call(eventName, new Object[0]);
    }

    public static void call(String eventName, Object arg1) {
        call(eventName, new Object[]{arg1});
    }

    public static void call(String eventName, Object arg1, Object arg2) {
        call(eventName, new Object[]{ arg1, arg2 });
    }

    public static void call(String eventName, Object arg1, Object arg2, Object arg3) {
        call(eventName, new Object[]{ arg1, arg2, arg3 });
    }

    public static void call(String eventName, Object arg1, Object arg2, Object arg3, Object arg4) {
        call(eventName, new Object[]{ arg1, arg2, arg3, arg4 });
    }

    public static void unsubscribe(String eventName, LuaFunction handler) {
        if (listeners.containsKey(eventName)) {
            listeners.get(eventName).remove(handler);
            return;
        }

        FLoader.LOGGER.error("Can not unsubscribe from event " + eventName + " because it does not exist!");
    }

    public static void call(String eventName, Object... args) {
        List<LuaFunction> handlers = listeners.get(eventName);

        if (handlers != null) {
            for (LuaFunction handler : handlers) {
                try {
                    LuaValue[] luaArgs = new LuaValue[args.length];

                    for (int i = 0; i < args.length; i++) {
                        Object arg = args[i];
                        if (arg instanceof Integer) luaArgs[i] = LuaValue.valueOf((Integer) arg);
                        else if (arg instanceof Float) luaArgs[i] = LuaValue.valueOf((double) (Float) arg);
                        else if (arg instanceof Boolean) luaArgs[i] = LuaValue.valueOf((Boolean) arg);
                        else luaArgs[i] = LuaValue.valueOf(arg.toString());
                    }

                    handler.invoke(luaArgs);
                } catch (Exception exc) {
                    FLoader.LOGGER.error("Error while calling event handler: " + exc.getMessage());
                }
            }
        }
    }
}