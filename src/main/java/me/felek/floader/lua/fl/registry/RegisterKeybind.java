package me.felek.floader.lua.fl.registry;

import me.felek.floader.utils.KeybindManager;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class RegisterKeybind extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue arg1, LuaValue arg2) {
        KeybindManager.registerBind(arg1.checkint(), arg2.checkfunction());
        return NIL;
    }
}
