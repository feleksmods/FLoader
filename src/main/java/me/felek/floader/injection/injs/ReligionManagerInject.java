package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class ReligionManagerInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws CannotCompileException, NotFoundException {
        CtClass cc = pool.get(clname);

        cc.getDeclaredField("lReligions").setModifiers(java.lang.reflect.Modifier.PUBLIC);
        cc.getDeclaredField("iReligionsSize").setModifiers(java.lang.reflect.Modifier.PUBLIC);

        CtMethod mLoad = cc.getDeclaredMethod("loadReligions");
        mLoad.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onReligionsInit\"); }");

        CtMethod mGet = cc.getDeclaredMethod("getReligion");
        mGet.insertBefore("if ($1 >= this.lReligions.size() || $1 < 0) return (age.of.civilizations2.jakowski.lukasz.ReligionManager$Religion)this.lReligions.get(0);");
    }
}
