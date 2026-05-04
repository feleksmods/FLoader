package me.felek.examplemod.mixin;

import me.felek.floader.api.mixin.At;
import me.felek.floader.api.mixin.Inject;
import me.felek.floader.api.mixin.Mixin;

@Mixin(target = "aoc.kingdoms.lukasz.jakowski.AA_Game")
public class ExampleMixin {
    @Inject(method = "create", at = At.RETURN)
    public static void create() {
        System.out.println("ExampleMixin called.");
    }
}
