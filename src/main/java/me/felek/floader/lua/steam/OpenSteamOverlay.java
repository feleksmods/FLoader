package me.felek.floader.lua.steam;

import age.of.civilizations2.jakowski.lukasz.Z_Other.ST.sUM;
import com.codedisaster.steamworks.SteamFriends;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class OpenSteamOverlay extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue dialog) {
        if (sUM.sUInited && sUM.uSF != null) {
            sUM.uSF.activateGameOverlay(SteamFriends.OverlayDialog.valueOf(dialog.checkjstring()));//TODO: error
            return LuaValue.TRUE;
        }
        return LuaValue.FALSE;
    }
}
