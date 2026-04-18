package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injected;

public class AllianceInject implements Injected {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtMethod m = cc.getDeclaredMethod("setAllianceName");
        m.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onAllianceFormed\", $1); }");
    }
}
