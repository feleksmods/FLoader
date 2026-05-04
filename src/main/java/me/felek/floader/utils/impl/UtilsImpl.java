package me.felek.floader.utils.impl;

import age.of.civilizations2.jakowski.lukasz.Console.Commands;
import age.of.civilizations2.jakowski.lukasz.GameCalendar;
import com.badlogic.gdx.Gdx;
import me.felek.floader.api.game.IUtils;

public class UtilsImpl implements IUtils {
    @Override
    public void log(String message) {
        Commands.addMessage(message);
    }

    @Override
    public int getTurnID() {
        return GameCalendar.TURNID;
    }

    @Override
    public String getMonthName(int monthID) {
        return GameCalendar.getMonthName(monthID);
    }

    @Override
    public int getFPS() {
        return Gdx.graphics.getFramesPerSecond();
    }

    @Override
    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    @Override
    public void exitGame(int code, boolean force) {
        if (force) System.exit(code);
        else Gdx.app.exit();
    }
}
