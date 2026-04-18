package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class DeclareWar extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue agressor, LuaValue defender) {
        CFG.core.declareWar(agressor.checkint(), defender.checkint(), true);
        return NIL;
    }
}
