package me.felek.floader.utils.bonus;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.lang.reflect.Field;

public class BonusParser {
    public static void apply(Object target, LuaTable data) {
        for (BonusType type : BonusType.values()) {
            LuaValue val = data.get(type.name());

            if (!val.isnil()) {
                try {
                    Field fil = target.getClass().getField(type.javaFieldName);

                    if (fil.getType() == float.class) {
                        fil.setFloat(target, val.tofloat());
                    } else if (fil.getType() == int.class) {
                        fil.setInt(target, val.toint());
                    } else if (fil.getType() == boolean.class) {
                        fil.setBoolean(target, val.toboolean());
                    }
                }catch (NoSuchFieldException e) {
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
