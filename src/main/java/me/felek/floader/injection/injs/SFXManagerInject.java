package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class SFXManagerInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        CtConstructor cons = cc.getDeclaredConstructors()[0];

        cons.insertAfter("{" +
                "  java.util.Iterator it = me.felek.floader.utils.RegistryManager.customStations.entrySet().iterator();" +
                "  while(it.hasNext()) {" +
                "    java.util.Map.Entry entry = (java.util.Map.Entry)it.next();" +
                "    String name = (String)entry.getKey();" +
                "    java.util.List tracks = (java.util.List)entry.getValue();" +
                "    this.lStations.add(name);" +
                "    this.lTitles.add(tracks);" +
                "  }" +
                "}");
    }
}
