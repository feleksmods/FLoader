package me.felek.floader.api.ui;

import age.of.civilizations2.jakowski.lukasz.Button.MenuElemUI;
import java.util.ArrayList;

public abstract class CustomMenu {
    public abstract void init(ArrayList<MenuElemUI> elements);
    public abstract void onAction(int elementId);
    public void onValueChange(int id, String value) {}
}