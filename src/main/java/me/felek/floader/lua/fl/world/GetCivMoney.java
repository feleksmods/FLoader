package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import me.felek.floader.utils.ExitCode;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetCivMoney extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue) {
        int id = luaValue.checkint();
        if (id < 0 || id >= CFG.core.getCivsSize()) {
            ExitCode.WORLD_CIV_INVALID_ID.throwFatalError("ID: " + id + " | Max Civs: " + CFG.core.getCivsSize());
        }
        if (CFG.core.getCiv(id) == null) {
            ExitCode.WORLD_CIV_NULL.throwFatalError("Civ object at ID " + id + " is null!");
        }
        return LuaValue.valueOf(CFG.core.getCiv(id).getGold());
    }
}
