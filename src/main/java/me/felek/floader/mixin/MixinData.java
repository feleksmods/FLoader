package me.felek.floader.mixin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MixinData {
    public final Class<?> mixinClass;
    public final String targetClassName;
    public final List<Method> methods = new ArrayList<>();

    public MixinData(Class<?> mixinClass, String targetClassName) {
        this.mixinClass = mixinClass;
        this.targetClassName = targetClassName;
    }
}
