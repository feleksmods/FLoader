package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injected;

public class AAGameInject implements Injected {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtMethod m = cc.getDeclaredMethod("create");

        m.insertBefore("{ me.felek.floader.FLoader.initModdingTools(); }");

        m.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onGameInitialization\"); }");
    }
}
