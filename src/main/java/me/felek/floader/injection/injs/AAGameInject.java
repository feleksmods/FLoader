package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class AAGameInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtMethod m = cc.getDeclaredMethod("create");

        m.insertBefore("{ me.felek.floader.FLoader.initModdingTools(); }");

        m.insertAfter("{ me.felek.floader.api.event.EventBus.call(\"onGameInitialization\"); }");
    }
}
