package me.felek.floader.lua.fl.utils;

import age.of.civilizations2.jakowski.lukasz.Console.Commands;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class Log extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue) {
        Commands.addMessage(luaValue.checkjstring());
        return NIL;
    }
}
