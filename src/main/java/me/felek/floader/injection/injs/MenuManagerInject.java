package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class MenuManagerInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        cc.getDeclaredMethod("setMenuID").insertAfter("{ me.felek.floader.api.event.EventBus.call(\"menuChanged\", eMenu); }");
        cc.getDeclaredMethod("setMenuIDWithoutAnim").insertAfter("{ me.felek.floader.api.event.EventBus.call(\"menuChanged\", eMenu); }");
    }
    
}
