package me.felek.floader.api.game;

public interface IUtils {
    void log(String message);
    int getTurnID();
    String getMonthName(int monthID);
    int getFPS();
    float getDeltaTime();
    void exitGame(int code, boolean force);
}
