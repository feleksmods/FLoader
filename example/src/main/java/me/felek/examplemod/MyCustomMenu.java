package me.felek.examplemod;

import age.of.civilizations2.jakowski.lukasz.Button.MenuElemUI;
import me.felek.floader.api.ui.CustomMenu;
import me.felek.floader.api.ui.UIFactory;

import java.util.ArrayList;

public class MyCustomMenu extends CustomMenu {
    private ArrayList<MenuElemUI> elements;

    @Override
    public void init(ArrayList<MenuElemUI> elements) {
        this.elements = elements;
        elements.add(UIFactory.createText("Enter name:", 50, 50, 400, 40));
        elements.add(UIFactory.createTextField("Felek", 50, 100, 400, 60));
    }

    @Override
    public void onAction(int id) {
    }

    @Override
    public void onValueChange(int id, String value) {
        if (id == 1) {
            System.out.println("set new value: " + value);
            elements.get(id).setTextE(value);
        }
    }
}
