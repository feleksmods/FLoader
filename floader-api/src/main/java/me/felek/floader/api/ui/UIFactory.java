package me.felek.floader.api.ui;

import age.of.civilizations2.jakowski.lukasz.*;
import age.of.civilizations2.jakowski.lukasz.Button.*;
import age.of.civilizations2.jakowski.lukasz.Button.Button_Icon;
import age.of.civilizations2.jakowski.lukasz.Button.Classic.*;
import age.of.civilizations2.jakowski.lukasz.Button.Flag.Button_Flag;
import age.of.civilizations2.jakowski.lukasz.Button.Game.*;
import age.of.civilizations2.jakowski.lukasz.Button.Stats.*;
import age.of.civilizations2.jakowski.lukasz.Button.View.*;
import age.of.civilizations2.jakowski.lukasz.Button.ZRest.Button_Add;
import age.of.civilizations2.jakowski.lukasz.Button2.*;
import age.of.civilizations2.jakowski.lukasz.Sliders.InGame.Slider_InGame;
import age.of.civilizations2.jakowski.lukasz.TextB.Text;

public class UIFactory {

    public static MenuElemUI createButton(String text, int x, int y, int w, int h) {
        return new Button_Classic_LR_Main(text, -1, x, y, w, h, true);
    }

    public static MenuElemUI createButtonClassic(String text, int x, int y, int w, int h) {
        return new Button_Classic(text, -1, x, y, w, h, true);
    }

    public static MenuElemUI createAddButton(String text, int x, int y, int w, int h, boolean clickable) {
        return new Button_Add(text, -1, x, y, w, h, clickable);
    }

    public static MenuElemUI createClose(int x, int y, int w, int h) {
        return new Button_Close(x, y, w, h);
    }

    public static MenuElemUI createTransparent(int x, int y, int w, int h) {
        return new Button_Transparent(x, y, w, h, true);
    }

    public static MenuElemUI createText(String text, int x, int y, int w, int h) {
        return new Text(text, -1, x, y, w, h);
    }

    public static MenuElemUI createTextStatic(String text, int x, int y, int w, int h) {
        return new Text_Static(text, -1, x, y, w, h);
    }

    public static MenuElemUI createTextDesc(String text, int x, int y, int w) {
        return new Text_Desc(text, x, y, w);
    }

    public static MenuElemUI createTextDesc2(String text, int x, int y, int w) {
        return new Text_Desc2_Special(text, x, y, w);
    }

    public static MenuElemUI createCheckbox(String text, int x, int y, int w, boolean state) {
        return new Button_Game_Checkbox(text, -1, x, y, w, true, state);
    }

    public static MenuElemUI createSlider(String text, int x, int y, int w, int h, int min, int max, int cur) {
        return new Slider_InGame(text, x, y, w, h, min, max, cur);
    }

    public static MenuElemUI createCivFlag(int civID, int x, int y, int w, int h, Button_Flag.ButtonFlagType type) {
        return new Button_Flag(civID, x, y, w, h, type);
    }

    public static MenuElemUI createGoldIcon(String label, String amount, int x, int y, int w, int h) {
        return new Button_Stats_Gold(label, amount, -1, x, y, w, h);
    }

    public static MenuElemUI createTextField(String text, int x, int y, int w, int h) {
        return new Button_Keyboard(text, x, y, w, h, ButtonM.TypeOfButton.KEYBOARD, true);
    }

    public static MenuElemUI createPopIcon(int row, String text, int provinceID, int totalPop, int x, int y, int w, boolean isAssimilate) {
        return new Button_View_Population(row, text, provinceID, totalPop, x, y, w, isAssimilate);
    }

    public static MenuElemUI createRankIcon(String text, int x, int y) {
        return new Button_Rank(text, x, y);
    }

    public static MenuElemUI createIcon(int imageID, int x, int y) {
        return new Button_Icon(imageID, x, y);
    }

    public static MenuElemUI createKeyboard(String text, int x, int y, int w, int h, ButtonM.TypeOfButton type, boolean clickable) {
        return new Button_Keyboard(text, x, y, w, h, type, clickable);
    }

    public static MenuElemUI createSpeed(String text, int x, int y, int w, int h, boolean clickable) {
        return new Button_Speed(text, -1, x, y, w, h, clickable);
    }
}