package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class MenuInitGameInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        CtMethod m = cc.getDeclaredMethod("loadBackground");
        m.insertAfter("{" +
                "age.of.civilizations2.jakowski.lukasz.Image customImg = me.felek.floader.utils.RegistryManager.getRandomCustomScreen();" +
                "if (customImg != null) {" +
                "   background = customImg;" +
                "   backgroundWidth = age.of.civilizations2.jakowski.lukasz.CFG.GAMEWIDTH;" +
                "   backgroundHeight = age.of.civilizations2.jakowski.lukasz.CFG.GAMEHEIGHT;" +
                "}" +
                "}");
    }
}
