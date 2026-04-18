package me.felek.floader.lua.fl.api;

import me.felek.floader.FLoader;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetLoaderName extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        return LuaValue.valueOf(FLoader.NAME);
    }
}
