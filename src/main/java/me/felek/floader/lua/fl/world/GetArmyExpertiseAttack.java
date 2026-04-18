package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import me.felek.floader.utils.ExitCode;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetArmyExpertiseAttack extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue arg) {
        int id = arg.checkint();
        if (id < 0 || id >= CFG.core.getCivsSize()) {
            ExitCode.WORLD_CIV_INVALID_ID.throwFatalError("ID: " + id + " | Max Civs: " + CFG.core.getCivsSize());
        }
        if (CFG.core.getCiv(id).civGD == null) {
            ExitCode.WORLD_CIV_DATA_CORRUPT.throwFatalError("civGD (GameData) for CivID " + id + " is null. Save file corrupt?");
        }
        return LuaValue.valueOf(CFG.core.getCiv(arg.checkint()).civGD.armyExpertiseAttack);
    }
}
