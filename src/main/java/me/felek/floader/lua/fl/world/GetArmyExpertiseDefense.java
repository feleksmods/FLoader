package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import me.felek.floader.utils.ExitCode;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetArmyExpertiseDefense extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue arg) {
        if (CFG.core == null) ExitCode.UNSAFE_CFG_ACCESS_ERROR.throwFatalError("CFG.core is NULL");
        if (arg.checkint() < 0 || arg.checkint() >= CFG.core.getCivsSize()) {
            ExitCode.WORLD_CIV_INVALID_ID.throwFatalError("CivID " + arg.checkint() + " out of [0-" + (CFG.core.getCivsSize()-1) + "]");
        }
        if (CFG.core.getCiv(arg.checkint()) == null) ExitCode.WORLD_CIV_NULL.throwFatalError("Civ object at " + arg.checkint() + " is null");

        return LuaValue.valueOf(CFG.core.getCiv(arg.checkint()).civGD.armyExpertiseDefense);
    }
}
