package me.felek.floader.lua.fl.player;

import age.of.civilizations2.jakowski.lukasz.CFG;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetPlayerCiv extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        return LuaValue.valueOf(CFG.core.getPlayer(CFG.PLAYER_TURN_ID).getCivId());
    }
}
