package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injected;

public class MenuInGameActionInfoTreasureIsEmptyInject implements Injected {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtConstructor cons = cc.getDeclaredConstructors()[0];
        cons.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onTreasuryEmpty\"); }");
    }
}
