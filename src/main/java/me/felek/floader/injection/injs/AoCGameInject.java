package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class AoCGameInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        javassist.CtClass cc = pool.get(clname);
        javassist.CtMethod m = cc.getDeclaredMethod("update");
        m.insertAfter("{ me.felek.floader.utils.KeybindManager.update(); }");
    }
}
