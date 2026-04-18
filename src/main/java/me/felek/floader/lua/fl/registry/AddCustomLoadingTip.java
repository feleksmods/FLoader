package me.felek.floader.lua.fl.registry;

import me.felek.floader.utils.RegistryManager;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class AddCustomLoadingTip extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue) {
        RegistryManager.registerLoadingTip(luaValue.checkjstring());

        return NIL;
    }
}
