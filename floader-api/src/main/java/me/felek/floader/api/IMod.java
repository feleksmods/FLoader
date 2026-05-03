package me.felek.floader.api;

public interface IMod {
    void onEnable();
    void onDisable();
    void onPreInitialization();
}
