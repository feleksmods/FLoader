package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class SetArmyExpertiseDefense extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue arg1, LuaValue arg2) {
        CFG.core.getCiv(arg1.checkint()).civGD.armyExpertiseDefense = arg2.checkint();
        return NIL;
    }
}
