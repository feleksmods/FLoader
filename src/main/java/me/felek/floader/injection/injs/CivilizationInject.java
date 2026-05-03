package me.felek.floader.injection.injs;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import me.felek.floader.injection.Injection;

public class CivilizationInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        cc.getDeclaredMethod("setCapitalProvID").insertAfter(
                "{ me.felek.floader.api.event.EventBus.call(\"onCapitalMoved\", ($w)this.iCivId, ($w)$1); }");

        cc.getDeclaredMethod("setIdeology").insertAfter(
                "{ me.felek.floader.api.event.EventBus.call(\"onIdeologyChanged\", ($w)this.iCivId, ($w)$1); }");

        cc.getDeclaredMethod("setTechLevel").insertAfter(
                "{ me.felek.floader.api.event.EventBus.call(\"onTechLeveledUp\", ($w)this.iCivId, ($w)$1); }");

        cc.getDeclaredMethod("setGold").insertAfter(
                "{ me.felek.floader.api.event.EventBus.call(\"onGoldChanged\", ($w)this.iCivId, ($w)$1); }");

        cc.getDeclaredMethod("setTruce3").insertAfter(
                "{ me.felek.floader.api.event.EventBus.call(\"onTruceSigned\", ($w)this.iCivId, ($w)$1, ($w)$2); }");
    }
}
