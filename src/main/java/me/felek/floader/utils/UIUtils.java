package me.felek.floader.utils;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Menu;
import age.of.civilizations2.jakowski.lukasz.MenuManager;
import age.of.civilizations2.jakowski.lukasz.Render;
import me.felek.floader.api.FLoader;
import me.felek.floader.api.ui.CustomMenu;
import me.felek.floader.api.ui.UIFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class UIUtils {//i hate javaassist
    public static void openCustomMenu(MenuManager mm, String menuKey) {
        RegistryManager reg = (RegistryManager) FLoader.registryManager;
        CustomMenu cMenu = (CustomMenu) reg.customMenus.get(menuKey);

        if (cMenu != null) {
            try {
                ArrayList elements = new ArrayList();
                elements.add(UIFactory.createButton(CFG.lang.get("Back"), 0, 0, 200, CFG.BUTTON_H));

                cMenu.init(elements);

                Menu gameMenu = new Menu();
                gameMenu.initMenu(null, 0, 0, CFG.GAMEWIDTH, CFG.GAMEHEIGHT, elements, true, true, true);

                Field menusField = MenuManager.class.getDeclaredField("menus");
                Field orderField = MenuManager.class.getDeclaredField("orderOfMenu");
                Field viewIdField = MenuManager.class.getDeclaredField("viewID");

                menusField.setAccessible(true);
                orderField.setAccessible(true);
                viewIdField.setAccessible(true);

                List menus = (List) menusField.get(mm);
                List orderOfMenu = (List) orderField.get(mm);

                int previousViewID = (Integer) viewIdField.get(mm);
                int newViewID = menus.size();

                menus.add(new ArrayList());
                ((List) menus.get(newViewID)).add(gameMenu);

                orderOfMenu.add(new ArrayList());
                ((List) orderOfMenu.get(newViewID)).add(Integer.valueOf(0));

                viewIdField.set(mm, newViewID);

                reg.activeCustomMenusMapping.put(Integer.valueOf(newViewID), cMenu);
                reg.customMenuPreviousView.put(Integer.valueOf(newViewID), Integer.valueOf(previousViewID));

                Render.updateRenderer();
                CFG.setRenderO(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
