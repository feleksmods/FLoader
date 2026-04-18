package me.felek.floader.lua.fl.unsafe;

import age.of.civilizations2.jakowski.lukasz.CFG;
import me.felek.floader.utils.annos.Unsafe;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

@Unsafe
public class GetCFG extends OneArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue) {
        switch (luaValue.checkjstring().toLowerCase()) {
            case "map":
                return LuaValue.userdataOf(CFG.map);
            default:
                return LuaValue.userdataOf(CFG.core);
        }
    }
}
