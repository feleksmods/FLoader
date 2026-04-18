package me.felek.floader.lua.fl.event;

import age.of.civilizations2.jakowski.lukasz.Event_Outcome;
import me.felek.floader.FLoader;
import org.luaj.vm2.LuaFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class EventLuaOutcome extends Event_Outcome {
    private static final long serialVersionUID = 1L;
    public static final Map<Integer, LuaFunction> luaCallbacks = new ConcurrentHashMap<>();
    private static final AtomicInteger ID_GEN = new AtomicInteger(1);

    public int callbackId = -1;

    public EventLuaOutcome() {}

    public EventLuaOutcome(LuaFunction func) {
        this.callbackId = ID_GEN.getAndIncrement();
        luaCallbacks.put(this.callbackId, func);
    }

    @Override
    public void outcomeAction() {
        LuaFunction func = luaCallbacks.get(this.callbackId);
        if (func != null) {
            try {
                func.call();
            } catch (Exception e) {
                FLoader.LOGGER.error("Error while processing event: " + e.getMessage());
            }
        } else {
            FLoader.LOGGER.error("FLoader Error: No Lua function found for ID " + callbackId);
        }
    }
}
