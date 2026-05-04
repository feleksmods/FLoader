package me.felek.floader.mixin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MixinRegistry {
    private static final Map<String, List<MixinData>> mixins = new ConcurrentHashMap<>();

    public static void register(MixinData data) {
        mixins.computeIfAbsent(data.targetClassName, k -> new ArrayList<>()).add(data);
    }

    public static boolean hasMixinsFor(String className) {
        return mixins.containsKey(className);
    }

    public static List<MixinData> getMixinsFor(String className) {
        return mixins.getOrDefault(className, Collections.emptyList());
    }

    public static void clear() {
        mixins.clear();
    }


}
