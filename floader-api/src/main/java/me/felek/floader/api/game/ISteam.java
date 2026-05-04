package me.felek.floader.api.game;

public interface ISteam {
    String getSteamName();
    long getPlayTime();
    void grantAchievement(String tag);
    void setRichPresence(String key, String value);
    void openOverlay(String dialog);
}
