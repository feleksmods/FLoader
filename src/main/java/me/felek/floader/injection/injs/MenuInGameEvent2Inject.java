package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class MenuInGameEvent2Inject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        CtMethod m = cc.getDeclaredMethod("loadEventIMG");
        m.insertBefore("{" +
                "String pictureKey = age.of.civilizations2.jakowski.lukasz.CFG.eventsManager.getEvent(age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event.EVENT_ID).getEventPicture();" +
                "if (me.felek.floader.api.FLoader.registryManager.resources.containsKey(pictureKey)) {" +
                "   age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event.eventsIMGs.clear();" +
                "   age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event.eventsIMGs.add((age.of.civilizations2.jakowski.lukasz.Image)me.felek.floader.api.FLoader.registryManager.resources.get(pictureKey));" +
                "   age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event.ANIMATION_IMG_ID = 0;" +
                "   age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event.ANIMATION_TIME = System.currentTimeMillis();" +
                "   return;" +
                "}" +
                "}");
    }
}
