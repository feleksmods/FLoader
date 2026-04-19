package me.felek.floader.injection.injs;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import me.felek.floader.injection.Injected;

public class ProvinceInject implements Injected {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        cc.getDeclaredMethod("setCivId", new CtClass[]{CtClass.intType, CtClass.booleanType, CtClass.booleanType})
                .insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onProvinceOwnerChanged\", ($w)this.iProvinceID, ($w)$1, ($w)$2); }");
    }
}
