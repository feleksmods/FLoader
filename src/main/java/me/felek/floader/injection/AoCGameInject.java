package me.felek.floader.injection;

import javassist.*;

public class AoCGameInject implements Injected{
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        javassist.CtClass cc = pool.get(clname);
        javassist.CtMethod m = cc.getDeclaredMethod("update");
        m.insertAfter("{ me.felek.floader.utils.KeybindManager.update(); }");
    }
}
