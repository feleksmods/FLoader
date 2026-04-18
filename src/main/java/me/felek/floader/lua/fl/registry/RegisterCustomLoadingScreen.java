package me.felek.floader.lua.fl.registry;

import me.felek.floader.utils.RegistryManager;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class RegisterCustomLoadingScreen extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue) {
        RegistryManager.registerLoadingScreen(luaValue.tojstring());
        return NIL;
    }
}
