package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class LangManagerInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        CtMethod mGetLOA = cc.getDeclaredMethod("getLOA");
        mGetLOA.insertBefore("{" +
                "try {" +
                "   int tipId = Integer.parseInt($1);" +
                "   int customCount = me.felek.floader.api.FLoader.registryManager.getTipsCount();" +
                "   int originalCount = this.iLNOT - customCount;" +
                "   if (tipId >= originalCount) {" +
                "       return me.felek.floader.api.FLoader.registryManager.getTip(tipId - originalCount);" +
                "   }" +
                "} catch (Exception e) {}" +
                "}");

        CtMethod mInit = cc.getDeclaredMethod("initLoadingBundle");
        mInit.insertAfter("{ this.iLNOT += me.felek.floader.api.FLoader.registryManager.getTipsCount(); }");
    }
}
