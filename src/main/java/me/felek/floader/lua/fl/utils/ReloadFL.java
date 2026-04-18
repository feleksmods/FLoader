package me.felek.floader.lua.fl.utils;

import me.felek.floader.FLoader;
import me.felek.floader.utils.annos.Unsafe;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

@Unsafe
public class ReloadFL extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        FLoader.reload();
        return NIL;
    }
}
