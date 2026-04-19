package me.felek.floader.lua.steam;

import age.of.civilizations2.jakowski.lukasz.Z_Other.ST.sUM;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetPlayTime extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        if (sUM.sUInited && sUM.sUT != null) {
            return LuaValue.valueOf(sUM.sUT.getSecondsSinceAppActive());
        }
        return LuaValue.valueOf(0);
    }
}
