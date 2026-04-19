package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Province;
import me.felek.floader.utils.ExitCode;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class IsCapital extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue) {
        if (CFG.core == null) {ExitCode.UNSAFE_CFG_ACCESS_ERROR.throwFatalError("CFG.core is NULL!");}
        if (luaValue.checkint()< 0 || luaValue.checkint()>= CFG.core.getProvinSize()) {
            ExitCode.WORLD_PROV_INVALID_ID.throwFatalError("Requested ProvID: " + luaValue.checkint()+ " | Total Provs: " + CFG.core.getProvinSize());
        }
        Province prov = CFG.core.getProv(luaValue.checkint());
        if (prov == null) {
            ExitCode.WORLD_PROV_NULL.throwFatalError("Province object at ID " + luaValue.checkint() + " is null in game memory.");
        }

        return LuaValue.valueOf(CFG.core.getProv(luaValue.checkint()).isCapital());
    }
}
