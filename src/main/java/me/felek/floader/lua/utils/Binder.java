package me.felek.floader.lua.utils;

import me.felek.floader.FLoader;
import me.felek.floader.utils.ExitCode;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.lang.reflect.Field;

public class Binder {
    public static void bind(LuaValue module, String luaName, Object target, String fieldName) {
        try {
            Field field = target instanceof Class ? ((Class<?>) target).getField(fieldName) : target.getClass().getField(fieldName);
            field.setAccessible(true);

            module.set("get" + luaName, new ZeroArgFunction() {
                @Override
                public LuaValue call() {
                    try {
                        Object val = field.get(target instanceof Class ? null : target);
                        if (val instanceof Integer) return valueOf((Integer) val);
                        if (val instanceof Long) return valueOf((Long) val);
                        if (val instanceof Float) return valueOf((Float) val);
                        if (val instanceof Boolean) return valueOf((Boolean) val);
                        if (val instanceof String) return valueOf((String) val);
                    } catch (Exception e) { e.printStackTrace(); }
                    return NIL;
                }
            });

            module.set("set" + luaName, new OneArgFunction() {
                @Override
                public LuaValue call(LuaValue arg) {
                    try {
                        Object t = target instanceof Class ? null : target;
                        if (field.getType() == int.class) field.setInt(t, arg.checkint());
                        else if (field.getType() == long.class) field.setLong(t, arg.checklong());
                        else if (field.getType() == float.class) field.setFloat(t, (float)arg.checkdouble());
                        else if (field.getType() == boolean.class) field.setBoolean(t, arg.checkboolean());
                        else if (field.getType() == String.class) field.set(t, arg.checkjstring());
                    } catch (Exception e) { e.printStackTrace(); }
                    return NIL;
                }
            });
        } catch (NoSuchFieldException e) {
            ExitCode.CORE_REFLECTION_ERROR.throwFatalError("Field '" + fieldName + "' not found in game class. Modding API is outdated!");
        } catch (Exception exc) {
            ExitCode.LUA_BINDING_ERROR.throwFatalError("Failed to bind " + luaName + " | Error: " + exc.getMessage());
        }
    }
}
