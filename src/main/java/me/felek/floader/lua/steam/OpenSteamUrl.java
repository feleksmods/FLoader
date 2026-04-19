package me.felek.floader.lua.steam;

import age.of.civilizations2.jakowski.lukasz.Z_Other.ST.sUM;
import com.codedisaster.steamworks.SteamFriends;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class OpenSteamUrl extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue arg) {
        if (sUM.sUInited && sUM.uSF != null) {
            sUM.uSF.activateGameOverlayToWebPage(arg.checkjstring(), SteamFriends.OverlayToWebPageMode.Default);
            return LuaValue.TRUE;
        }
        return LuaValue.FALSE;
    }
}
