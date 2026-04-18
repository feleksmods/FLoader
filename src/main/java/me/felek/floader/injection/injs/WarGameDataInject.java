package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injected;

public class WarGameDataInject implements Injected {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        CtConstructor[] constructors = cc.getDeclaredConstructors();
        for (CtConstructor constructor : constructors) {
            if (constructor.getSignature().equals("(II)V")) {
                constructor.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onWarDeclared\", ($w)$1, ($w)$2); }");
            }
        }
    }
}
