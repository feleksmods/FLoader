package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class TouchInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtMethod m = cc.getDeclaredMethod("actionUp_setActiveProvinceID");
        m.insertBefore("{ me.felek.floader.api.event.EventBus.call(\"onProvinceClick\", ($w)$1); }");
    }
}
