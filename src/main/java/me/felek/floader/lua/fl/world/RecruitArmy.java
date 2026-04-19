package me.felek.floader.lua.fl.world;

import age.of.civilizations2.jakowski.lukasz.CFG;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

public class RecruitArmy extends ThreeArgFunction {
    @Override
    public LuaValue call(LuaValue arg1, LuaValue arg2, LuaValue arg3) {//TODO: checks for ExitCode there!
        CFG.core.getCiv(arg1.checkint()).recruitArmy(arg2.checkint(), arg3.checkint());
        return NIL;
    }
}
