package me.felek.floader.api.game;

public interface ISettings {
    boolean getFogOfWar();
    void setFogOfWar(boolean val);
    boolean getSandboxMode();
    void setSandboxMode(boolean val);
    int getGameWidth();
    int getGameHeight();
    float getGuiScale();
    void setGuiScale(float scale);
    void setWindowTitle(String title);
}
