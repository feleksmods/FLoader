package me.felek.floader.lua.fl.api;

import com.badlogic.gdx.Gdx;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class ExitGame extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue code, LuaValue force) {
        if (force.checkboolean()) {
            Runtime.getRuntime().exit(code.checkint());
        } else {
            Gdx.app.exit();
            System.exit(code.checkint());
        }

        return NIL;
    }
}
