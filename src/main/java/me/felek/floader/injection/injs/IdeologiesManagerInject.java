package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class IdeologiesManagerInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        cc.getDeclaredField("lIdeologies").setModifiers(java.lang.reflect.Modifier.PUBLIC);

        CtMethod mLoad = cc.getDeclaredMethod("loadIdeologies");
        mLoad.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onIdeologiesInit\"); }");
    }
}
