package me.felek.examplemod;

import me.felek.floader.api.IMod;
import me.felek.floader.api.Mod;
import me.felek.floader.api.event.EventBus;

@Mod(id = "exampleMod", version = "1.0", author = "FelekDevYT", description = "So cool mod for FLoader!")
public class ExampleMod implements IMod {

    @Override
    public void onEnable() {
        System.out.println("enabled");
    }

    @Override
    public void onDisable() {
        System.out.println("disabled");
    }

    @Override
    public void onPreInitialization() {
        EventBus.subscribe("onGameInitialization", (args) -> {
            System.out.println("INITIALIZATION!");
        });
    }
}