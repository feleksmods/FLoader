package me.felek.floader.lua.steam;

import age.of.civilizations2.jakowski.lukasz.Z_Other.ST.sUM;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

public class SetRichPresence extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue arg, LuaValue value) {
        if (sUM.sUInited && sUM.uSF != null) {
            sUM.uSF.setRichPresence(arg.checkjstring(), value.checkjstring());
            return LuaValue.TRUE;
        }
        return LuaValue.FALSE;
    }
}
