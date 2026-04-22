package me.felek.floader.lua.fl.audio;

import age.of.civilizations2.jakowski.lukasz.CFG;
import me.felek.floader.utils.RegistryManager;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import java.util.ArrayList;
import java.util.List;

public class AddStation extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue arg1, LuaValue arg2) {
        String name = arg1.checkjstring();
        LuaTable table = arg2.checktable();
        List<String> tracks = new ArrayList<>();
        for (int i = 1; i <= table.length(); i++) {
            tracks.add(table.get(i).checkjstring());
        }

        RegistryManager.registerStation(name, tracks);
        return NIL;
    }
}
