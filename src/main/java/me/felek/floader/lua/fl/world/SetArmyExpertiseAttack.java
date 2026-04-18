package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class SetArmyExpertiseAttack extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue arg1, LuaValue arg2) {
        CFG.core.getCiv(arg1.checkint()).civGD.armyExpertiseAttack = arg2.checkint();
        return NIL;
    }
}
