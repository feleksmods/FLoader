package me.felek.floader.lua.fl.settings;

import com.badlogic.gdx.Gdx;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetWindowTitle extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue arg) {
        try {
            Gdx.graphics.setTitle(arg.checkjstring());
        } catch (Exception exc) {
            //TODO: error
        }

        return NIL;
    }
}
