package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

public class SetRelation extends ThreeArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue1, LuaValue luaValue2) {
        CFG.core.setCivRelationOfCivB(luaValue.checkint(), luaValue1.checkint(), luaValue2.checkint());
        return NIL;
    }
}
