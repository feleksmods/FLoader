package me.felek.floader.injection;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;

@FunctionalInterface
public interface Injected {
    void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException;
}
