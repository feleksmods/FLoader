package me.felek.floader.injection.injs;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import me.felek.floader.injection.Injection;

public class ProvinceInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        cc.getDeclaredMethod("setCivId", new CtClass[]{CtClass.intType, CtClass.booleanType, CtClass.booleanType})
                .insertAfter("{ me.felek.floader.api.event.EventBus.call(\"onProvinceOwnerChanged\", ($w)this.iProvinceID, ($w)$1, ($w)$2); }");
    }
}
