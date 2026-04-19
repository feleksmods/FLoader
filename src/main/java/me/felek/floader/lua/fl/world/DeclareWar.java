package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import me.felek.floader.utils.ExitCode;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class DeclareWar extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue agressor, LuaValue defender) {
        if (CFG.core == null) ExitCode.UNSAFE_CFG_ACCESS_ERROR.throwFatalError("CFG.core is NULL");
        if (agressor.checkint() < 0 || agressor.checkint() >= CFG.core.getCivsSize()) {
            ExitCode.WORLD_CIV_INVALID_ID.throwFatalError("CivID " + agressor.checkint() + " out of [0-" + (CFG.core.getCivsSize()-1) + "]");
        }
        if (defender.checkint() < 0 || defender.checkint() >= CFG.core.getCivsSize()) {
            ExitCode.WORLD_CIV_INVALID_ID.throwFatalError("CivID " + defender.checkint() + " out of [0-" + (CFG.core.getCivsSize()-1) + "]");
        }
        if (CFG.core.getCiv(agressor.checkint()) == null) ExitCode.WORLD_CIV_NULL.throwFatalError("Civ object at " + agressor.checkint() + " is null");

        CFG.core.declareWar(agressor.checkint(), defender.checkint(), true);
        return NIL;
    }
}
