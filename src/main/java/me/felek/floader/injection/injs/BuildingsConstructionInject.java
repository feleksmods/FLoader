package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class BuildingsConstructionInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtMethod m = cc.getDeclaredMethod("onConstructedRun");
        m.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onBuildingConstructed\", ($w)$1, ($w)this.iProviID, this.constructionType.toString()); }");
    }
}
