package me.felek.floader.api.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class EventBus {
    private static final Map<String, List<Consumer<Object[]>>> listeners = new ConcurrentHashMap<>();

    public static void subscribe(String eventName, Consumer<Object[]> handler) {
        listeners.computeIfAbsent(eventName, mf -> new ArrayList<>()).add(handler);
    }

    public static void call(String eventName) {
        call(eventName, new Object[0]);
    }

    public static void call(String eventName, Object arg1) {
        call(eventName, new Object[]{arg1});
    }

    public static void call(String eventName, Object arg1, Object arg2) {
        call(eventName, new Object[]{arg1, arg2});
    }

    public static void call(String eventName, Object arg1, Object arg2, Object arg3) {
        call(eventName, new Object[]{arg1, arg2, arg3});
    }

    public static void call(String eventName, Object arg1, Object arg2, Object arg3, Object arg4) {
        call(eventName, new Object[]{arg1, arg2, arg3, arg4});
    }

    public static void call(String name, Object... args) {
        List<Consumer<Object[]>> handlers = listeners.get(name);
        if (handlers != null) {
            for (Consumer<Object[]> handler : handlers) {
                try {
                    handler.accept(args);
                } catch (Exception e) {
                    System.err.println("ERROR WHILE CALLING EVENT");//TODO: error handling
                    e.printStackTrace();
                }
            }
        }
    }
}
