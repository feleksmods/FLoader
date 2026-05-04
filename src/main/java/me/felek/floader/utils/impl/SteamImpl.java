package me.felek.floader.utils.impl;

import age.of.civilizations2.jakowski.lukasz.Z_Other.ST.sUM;
import com.codedisaster.steamworks.SteamFriends;
import me.felek.floader.api.game.ISteam;

public class SteamImpl implements ISteam {
    @Override
    public String getSteamName() {
        return (sUM.sUInited && sUM.uSF != null) ? sUM.uSF.getPersonaName() : "Steam Not Inited";
    }

    @Override
    public long getPlayTime() {
        return (sUM.sUInited && sUM.sUT != null) ? sUM.sUT.getSecondsSinceAppActive() : 0;
    }

    @Override
    public void grantAchievement(String tag) {
        if (sUM.sUInited && sUM.sUI != null) {
            sUM.sUI.setAchievement(tag);
            sUM.sUI.storeStats();
        }
    }

    @Override
    public void setRichPresence(String key, String value) {
        if (sUM.sUInited && sUM.uSF != null) {
            sUM.uSF.setRichPresence(key, value);
        }
    }

    @Override
    public void openOverlay(String dialog) {
        if (sUM.sUInited && sUM.uSF != null) {
            sUM.uSF.activateGameOverlay(SteamFriends.OverlayDialog.valueOf(dialog));
        }
    }
}
