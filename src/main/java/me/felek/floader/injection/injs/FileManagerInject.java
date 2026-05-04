package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class FileManagerInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);
        CtMethod m = cc.getDeclaredMethod("loadFile", new CtClass[]{ pool.get("java.lang.String") });

        m.insertBefore("{" +
                "  com.badlogic.gdx.files.FileHandle modFile = me.felek.floader.utils.FolderManager.getModFile($1);" +
                "  if (modFile != null) {" +
                "    return modFile;" +
                "  }" +
                "}");
    }
}
