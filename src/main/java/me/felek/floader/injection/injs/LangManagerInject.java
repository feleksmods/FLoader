package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injected;

public class LangManagerInject implements Injected {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        CtMethod mGetLOA = cc.getDeclaredMethod("getLOA");
        mGetLOA.insertBefore("{" +
                "try {" +
                "   int tipId = Integer.parseInt($1);" +
                "   int customCount = me.felek.floader.utils.RegistryManager.getTipsCount();" +
                "   int originalCount = this.iLNOT - customCount;" +
                "   if (tipId >= originalCount) {" +
                "       return me.felek.floader.utils.RegistryManager.getTip(tipId - originalCount);" +
                "   }" +
                "} catch (Exception e) {}" +
                "}");

        CtMethod mInit = cc.getDeclaredMethod("initLoadingBundle");
        mInit.insertAfter("{ this.iLNOT += me.felek.floader.utils.RegistryManager.getTipsCount(); }");
    }
}
