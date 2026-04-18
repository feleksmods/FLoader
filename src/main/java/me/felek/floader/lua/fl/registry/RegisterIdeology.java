package me.felek.floader.lua.fl.registry;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Ideology;
import me.felek.floader.utils.bonus.BonusParser;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.util.List;

public class RegisterIdeology extends VarArgFunction {
    @Override
    public Varargs invoke(Varargs args) {
        String name = args.arg(1).checkjstring();
        String tag = args.arg(2).checkjstring();
        LuaTable colorTab = args.arg(3).checktable();
        LuaTable bonuses = args.arg(4).checktable();

        int r = colorTab.get(1).optint(200);
        int g = colorTab.get(2).optint(200);
        int b = colorTab.get(3).optint(200);

        Ideology ideology = new Ideology(
                name, tag, r, g, b,
                0.15f, 0.1f, 6, 4, 2, 1.0f, 0.25f, 2, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2, 20, 12,
                -1, 0, 0.6f, 1.0f, "RANDOM", false, 0, ""
        );

        BonusParser.apply(ideology, bonuses);
        try {
            List<Ideology> list = (List<Ideology>) CFG.ideologiesMgr.getClass().getField("lIdeologies").get(CFG.ideologiesMgr);
            list.add(ideology);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return valueOf(CFG.ideologiesMgr.getIdeologiesSize() - 1);
    }
}
