package me.felek.floader.lua.fl.utils;

import age.of.civilizations2.jakowski.lukasz.GameCalendar;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetMonthName extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue) {
        return LuaValue.valueOf(GameCalendar.getMonthName(luaValue.checkint()));
    }
}
