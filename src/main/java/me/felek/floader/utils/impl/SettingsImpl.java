package me.felek.floader.utils.impl;

import age.of.civilizations2.jakowski.lukasz.CFG;
import com.badlogic.gdx.Gdx;
import me.felek.floader.api.game.ISettings;

public class SettingsImpl implements ISettings {
    @Override
    public boolean getFogOfWar() { return CFG.FOG_OF_WAR == 2; }
    @Override
    public void setFogOfWar(boolean val) { CFG.FOG_OF_WAR = val ? 2 : 0; }

    @Override
    public boolean getSandboxMode() { return CFG.SANDBOX_MODE; }
    @Override
    public void setSandboxMode(boolean val) { CFG.SANDBOX_MODE = val; }

    @Override
    public int getGameWidth() { return CFG.GAMEWIDTH; }
    @Override
    public int getGameHeight() { return CFG.GAMEHEIGHT; }

    @Override
    public float getGuiScale() { return CFG.GUI_SCALE; }
    @Override
    public void setGuiScale(float scale) { CFG.GUI_SCALE = scale; }

    @Override
    public void setWindowTitle(String title) {
        Gdx.graphics.setTitle(title);
    }
}
