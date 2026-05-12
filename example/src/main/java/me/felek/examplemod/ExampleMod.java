package me.felek.examplemod;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Image;
import age.of.civilizations2.jakowski.lukasz.MenuManager;
import me.felek.floader.api.FLoader;
import me.felek.floader.api.IMod;
import me.felek.floader.api.Mod;
import me.felek.floader.api.event.EventBus;

@Mod(id = "exampleMod", version = "1.0", author = "FelekDevYT", description = "So cool mod for FLoader!")
public class ExampleMod implements IMod {

    @Override
    public void onEnable() {
        System.out.println("enabled");
        FLoader.registryManager.registerMenu("lol", new MyCustomMenu());
    }

    @Override
    public void onDisable() {
        System.out.println("disabled");
    }

    @Override
    public void onPreInitialization() {
        FLoader.registryManager.registerCommand("something", (args) -> {
            FLoader.registryManager.openMenu("lol");
        });
    }
}