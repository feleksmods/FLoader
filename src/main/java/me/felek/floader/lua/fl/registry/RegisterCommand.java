package me.felek.floader.lua.fl.registry;

import me.felek.floader.utils.RegistryManager;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class RegisterCommand extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
        RegistryManager.registerCommand(luaValue.checkjstring(), luaValue1.checkfunction());
        return NIL;
    }
}
