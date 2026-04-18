package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Province;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetProvinceOwner extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue) {
        Province prov = CFG.core.getProv(luaValue.checkint());
        return LuaValue.valueOf(prov.getCivId());
    }
}
