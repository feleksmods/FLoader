package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class PeaceTreatyDataInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtMethod m = cc.getDeclaredMethod("preparePeaceTreatyToSend");
        m.insertBefore("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onPeaceSigned\", ($w)$1); }");
    }
}
