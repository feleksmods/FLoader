package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class GameActionInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        CtMethod m = cc.getDeclaredMethod("startNewTurn_End");
        m.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onTurnEnd\"); }");

        CtMethod civDestroyed = cc.getDeclaredMethod("civDestroyed");
        civDestroyed.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onCivDestroyed\", ($w)$1); }");
    }
}
