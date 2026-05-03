package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class MenuInGameActionInfoTreasureIsEmptyInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtConstructor cons = cc.getDeclaredConstructors()[0];
        cons.insertAfter("{ me.felek.floader.api.event.EventBus.call(\"onTreasuryEmpty\"); }");
    }
}
