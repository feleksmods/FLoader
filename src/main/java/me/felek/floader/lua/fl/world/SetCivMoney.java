package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class SetCivMoney extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue civId, LuaValue gold) {
        CFG.core.getCiv(civId.checkint()).setGold(gold.checkint());
        return NIL;
    }
}
