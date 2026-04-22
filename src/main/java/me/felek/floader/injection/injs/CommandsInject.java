package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class CommandsInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        CtMethod m = cc.getDeclaredMethod("execute");
        m.insertBefore("{ if (me.felek.floader.utils.RegistryManager.executeCommand($1)) return; }");
    }
}
