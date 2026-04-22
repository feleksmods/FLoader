package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class EventOutcomeBuildDestroy implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtMethod m = cc.getDeclaredMethod("outcomeAction");
        m.insertAfter("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onBuildingDestroyed\", ($w)this.iCivID, ($w)this.buildingID); }");
    }
}
