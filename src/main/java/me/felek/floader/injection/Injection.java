package me.felek.floader.injection;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;

@FunctionalInterface
public interface Injection {
    void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException;
}
