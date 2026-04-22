package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class FileManagerInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtMethod m = cc.getDeclaredMethod("loadFile", new CtClass[]{ pool.get("java.lang.String") });

        m.insertBefore("{" +
                "  String modPath = me.felek.floader.utils.FolderManager.getAssetPath($1);" +
                "  if (modPath != null) {" +
                "    return com.badlogic.gdx.Gdx.files.absolute(modPath);" +
                "  }" +
                "}");
    }
}
