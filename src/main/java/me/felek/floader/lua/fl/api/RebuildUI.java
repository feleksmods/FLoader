package me.felek.floader.lua.fl.api;

import age.of.civilizations2.jakowski.lukasz.Render;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class RebuildUI extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        Render.updateRenderer();
        return NIL;
    }
}
