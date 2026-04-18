package me.felek.floader.lua.fl.utils;

import age.of.civilizations2.jakowski.lukasz.GameCalendar;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetTurnID extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        return LuaValue.valueOf(GameCalendar.TURNID);
    }
}
