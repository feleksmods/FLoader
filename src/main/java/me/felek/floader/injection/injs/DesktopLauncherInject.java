package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class DesktopLauncherInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        CtMethod m = cc.getDeclaredMethod("main");
        m.insertBefore(" { me.felek.floader.FLoader.init(); System.out.println(\"FLoader injected into AoH2: DE!\");} ");//INJECTION
    }
}
