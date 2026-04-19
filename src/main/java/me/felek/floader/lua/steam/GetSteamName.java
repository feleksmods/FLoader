package me.felek.floader.lua.steam;

import age.of.civilizations2.jakowski.lukasz.Z_Other.ST.sUM;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetSteamName extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        if (sUM.sUInited && sUM.uSF != null) {
            return LuaValue.valueOf(sUM.uSF.getPersonaName());
        }
        return LuaValue.valueOf("Steam Not Inited");//TODO: error
    }
}
