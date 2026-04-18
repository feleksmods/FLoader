package me.felek.floader.lua.fl.api;

import me.felek.floader.mod.Mod;
import me.felek.floader.mod.ModManager;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetModInfo extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue arg) {
        LuaValue table = LuaValue.tableOf();
        Mod mod = ModManager.getModByName(arg.checkjstring());
        if (mod == null) {
            return NIL;
        }

        table.set("name", mod.name);
        table.set("author", mod.author);
        table.set("description", mod.description);
        table.set("version", mod.version);
        table.set("mainFile", mod.mainFile);

        return table;
    }
}
