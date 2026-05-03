package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class AllianceInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtMethod m = cc.getDeclaredMethod("setAllianceName");
        m.insertAfter("{ me.felek.floader.api.event.EventBus.call(\"onAllianceFormed\", $1); }");
    }
}
