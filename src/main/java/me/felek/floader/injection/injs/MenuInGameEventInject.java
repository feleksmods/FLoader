package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class MenuInGameEventInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        CtMethod mLoad = cc.getDeclaredMethod("loadEventIMG");
        mLoad.insertBefore("{" +
                "String pictureKey = age.of.civilizations2.jakowski.lukasz.CFG.eventsManager.getEvent(EVENT_ID).getEventPicture();" +
                "if (me.felek.floader.api.FLoader.registryManager.containsResource(pictureKey)) {" +
                "   eventsIMGs.clear();" +
                "   eventsIMGs.add((age.of.civilizations2.jakowski.lukasz.Image)me.felek.floader.api.FLoader.registryManager.getResource(pictureKey));" +
                "   ANIMATION_IMG_ID = 0;" +
                "   ANIMATION_TIME = System.currentTimeMillis();" +
                "   return;" +
                "}" +
                "}");

        CtMethod m = cc.getDeclaredMethod("setVisibleM");
        m.insertBefore("{" +
                "if (!$1) {" + //if (visilbe==false)
                "   String pictureKey = age.of.civilizations2.jakowski.lukasz.CFG.eventsManager.getEvent(EVENT_ID).getEventPicture();" +
                "   if (me.felek.floader.api.FLoader.registryManager.containsResource(pictureKey)) {" +
                "       eventsIMGs.clear();" +
                "   }" +
                "}" +
                "}");
    }
}
