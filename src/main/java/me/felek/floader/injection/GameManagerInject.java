package me.felek.floader.injection;

import javassist.*;

public class GameManagerInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtMethod mNuke = cc.getDeclaredMethod("nukeProvince");
        mNuke.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"sendNukes\", ($w)$2, ($w)$3); }");
    }
}
