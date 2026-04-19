package me.felek.floader.lua.fl.registry;

import age.of.civilizations2.jakowski.lukasz.*;
import me.felek.floader.utils.ExitCode;
import me.felek.floader.utils.RegistryManager;
import me.felek.floader.utils.bonus.BonusParser;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class RegisterCustomReligion extends VarArgFunction {
    @Override
    public Varargs invoke(Varargs args) {
        String name = args.arg(1).checkjstring();
        LuaTable colorTab = args.arg(2).checktable();
        LuaTable bonusesTable = args.arg(3).checktable();
        String imgKey = args.arg(4).optjstring("");

        if (CFG.religionManager == null) {
            ExitCode.UNSAFE_CFG_ACCESS_ERROR.throwFatalError("ReligionManager is null during registration");
        }
        if (CFG.religionManager.lReligions == null) {
            CFG.religionManager.lReligions = new ArrayList<>();
        }

        ReligionManager.Religion rel = new ReligionManager.Religion();
        rel.Name = name;
        rel.Color = new float[] {
                (float)colorTab.get(1).optdouble(255.0) / 255.0f,
                (float)colorTab.get(2).optdouble(255.0) / 255.0f,
                (float)colorTab.get(3).optdouble(255.0) / 255.0f
        };

        try {
            BonusParser.apply(rel, bonusesTable);
        } catch (Exception e) {
            ExitCode.LUA_BINDING_ERROR.throwFatalError("Failed to apply religion bonuses: " + e.getMessage());
        }
        CFG.religionManager.lReligions.add(rel);
        Image icon = null;
        if (!imgKey.isEmpty() && RegistryManager.resources.containsKey(imgKey)) {
            icon = RegistryManager.resources.get(imgKey);
        } else {
            if (CFG.religionManager.religionImages != null && !CFG.religionManager.religionImages.isEmpty()) {
                icon = CFG.religionManager.religionImages.get(0);
            } else {
                icon = IMGManager.getIMG(Images.pix255);
            }
        }
        CFG.religionManager.religionImages.add(icon);

        try {
            Field field = ReligionManager.class.getDeclaredField("iReligionsSize");
            field.setAccessible(true);
            field.setInt(CFG.religionManager, CFG.religionManager.lReligions.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return valueOf(CFG.religionManager.lReligions.size() - 1);
    }
}
