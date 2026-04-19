package me.felek.floader.lua.steam;

import age.of.civilizations2.jakowski.lukasz.Z_Other.ST.sUM;
import me.felek.floader.FLoader;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;


public class GrantSteamAchievement extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue arg) {
        String tag = arg.checkjstring();
        try {
            if (sUM.sUInited && sUM.sUI != null) {
                sUM.sUI.setAchievement(tag);
                sUM.sUI.storeStats();
                return LuaValue.TRUE;
            }
        } catch (Exception exc) {
            FLoader.LOGGER.error("ERROR: " + exc.toString());//TODO: smth
        }
        return LuaValue.FALSE;
    }
}
