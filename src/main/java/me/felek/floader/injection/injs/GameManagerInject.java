package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class GameManagerInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        //TODO: fix
//        CtMethod mNuke = cc.getDeclaredMethod("nukeProvince");
//        mNuke.insertAfter("{ me.felek.floader.api.event.EventBus.call(\"sendNukes\", ($w)$2, ($w)$3); }");
    }
}
