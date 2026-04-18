package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injected;

public class GameActionInject implements Injected {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        CtMethod m = cc.getDeclaredMethod("startNewTurn_End");
        m.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onTurnEnd\"); }");
    }
}
