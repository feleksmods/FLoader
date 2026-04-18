package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetDiploPoints extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue) {
        return LuaValue.valueOf(CFG.core.getCiv(luaValue.checkint()).getDiploPoints());
    }
}
