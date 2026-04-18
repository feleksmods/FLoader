package me.felek.floader.lua.fl.api;

import me.felek.floader.mod.Mod;
import me.felek.floader.mod.ModManager;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetModsList extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        LuaValue table = LuaValue.tableOf();
        for (int i = 0; i < ModManager.LOADED_MODS.size(); i++) {
            table.set(i+1, LuaValue.valueOf(ModManager.LOADED_MODS.get(i).name));
        }

        return table;
    }
}
