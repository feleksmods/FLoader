package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import me.felek.floader.utils.ExitCode;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class SetCivMoney extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue civId, LuaValue gold) {
        if (CFG.core == null) ExitCode.UNSAFE_CFG_ACCESS_ERROR.throwFatalError("CFG.core is NULL");
        if (civId.checkint() < 0 || civId.checkint() >= CFG.core.getCivsSize()) {
            ExitCode.WORLD_CIV_INVALID_ID.throwFatalError("CivID " + civId.checkint() + " out of [0-" + (CFG.core.getCivsSize()-1) + "]");
        }
        if (CFG.core.getCiv(civId.checkint()) == null) ExitCode.WORLD_CIV_NULL.throwFatalError("Civ object at " + civId.checkint() + " is null");

        CFG.core.getCiv(civId.checkint()).setGold(gold.checkint());
        return NIL;
    }
}
