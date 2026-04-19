package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Province;
import me.felek.floader.utils.ExitCode;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetProvincesCount extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue civId) {
        if (CFG.core == null) {
            ExitCode.UNSAFE_CFG_ACCESS_ERROR.throwFatalError("CFG.core is NULL!");}
        if (civId.checkint()< 0 || civId.checkint()>= CFG.core.getProvinSize()) {
            ExitCode.WORLD_PROV_INVALID_ID.throwFatalError("Requested ProvID: " + civId.checkint()+ " | Total Provs: " + CFG.core.getProvinSize());
        }
        Province prov = CFG.core.getProv(civId.checkint());
        if (prov == null) {
            ExitCode.WORLD_PROV_NULL.throwFatalError("Province object at ID " + civId.checkint() + " is null in game memory.");
        }

        return LuaValue.valueOf(CFG.core.getCiv(civId.checkint()).getNumOfProvs());
    }
}
