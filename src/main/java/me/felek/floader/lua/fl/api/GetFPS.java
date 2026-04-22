package me.felek.floader.lua.fl.api;

import com.badlogic.gdx.Gdx;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetFPS extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        return LuaValue.valueOf(Gdx.graphics.getFramesPerSecond());
    }
}
