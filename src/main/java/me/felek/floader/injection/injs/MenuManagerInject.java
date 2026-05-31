package me.felek.floader.injection.injs;

import javassist.*;
import me.felek.floader.injection.Injection;

public class MenuManagerInject implements Injection {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        cc.getDeclaredMethod("setMenuID").insertAfter("{ me.felek.floader.api.event.EventBus.call(\"menuChanged\", $1); }");
        cc.getDeclaredMethod("setMenuIDWithoutAnim").insertAfter("{ me.felek.floader.api.event.EventBus.call(\"menuChanged\", $1); }");

        cc.getDeclaredField("menus").setModifiers(Modifier.PUBLIC);
        cc.getDeclaredField("orderOfMenu").setModifiers(Modifier.PUBLIC);
        cc.getDeclaredField("viewID").setModifiers(Modifier.PUBLIC);
        cc.getDeclaredField("keyboard").setModifiers(Modifier.PUBLIC);
        cc.getDeclaredField("keyboardMode").setModifiers(Modifier.PUBLIC);
        cc.getDeclaredField("activeMenuElemeID").setModifiers(Modifier.PUBLIC);
        cc.getDeclaredField("keyboardActiveMenuElementID").setModifiers(Modifier.PUBLIC);

        CtMethod setByInt = CtNewMethod.make(
                "public void setMenuIDByInt(int id) {" +
                        "  this.resetHoverActive();" +
                        "  this.keyboard.setVisibleM(false);" +
                        "  this.fromViewID = this.viewID;" +
                        "  this.toViewID = id;" +
                        "  this.viewID = id;" +
                        "  age.of.civilizations2.jakowski.lukasz.CFG.setRenderO(true);" +
                        "  this.updateViewID();" +
                        "}", cc);
        cc.addMethod(setByInt);

        CtMethod actionElem = cc.getDeclaredMethod("actionElem");
        actionElem.insertBefore(
                "{" +
                        "  me.felek.floader.utils.RegistryManager reg = (me.felek.floader.utils.RegistryManager)me.felek.floader.api.FLoader.registryManager;" +
                        "  java.lang.Integer currentViewObj = new java.lang.Integer(this.viewID);" +
                        "  Object activeC = reg.activeCustomMenusMapping.get(currentViewObj);" +
                        "  if (activeC != null) {" +
                        "    if ($2 == 0) {" +
                        "      java.lang.Integer prevView = (java.lang.Integer) reg.customMenuPreviousView.get(currentViewObj);" +
                        "      if (prevView != null) {" +
                        "         this.setMenuIDByInt(prevView.intValue());" +
                        "      } else {" +
                        "         this.setMenuID(age.of.civilizations2.jakowski.lukasz.View.eMAINMENU);" +
                        "      }" +
                        "      reg.activeCustomMenusMapping.remove(currentViewObj);" +
                        "      reg.customMenuPreviousView.remove(currentViewObj);" +
                        "    } else {" +
                        "      age.of.civilizations2.jakowski.lukasz.Button.MenuElemUI clickedElem = this.getMenuElement($1, $2);" +
                        "      if (clickedElem instanceof age.of.civilizations2.jakowski.lukasz.Button.Button_Keyboard) {" +
                        "        age.of.civilizations2.jakowski.lukasz.CFG.showKeyboard($1, $2);" +
                        "      }" +
                        "      ((me.felek.floader.api.ui.CustomMenu)activeC).onAction($2 - 1);" +
                        "    }" +
                        "    return;" +
                        "  }" +
                        "}"
        );

        CtMethod back = cc.getDeclaredMethod("onBackPressed");
        back.insertBefore(
                "{" +
                        "  me.felek.floader.utils.RegistryManager reg = (me.felek.floader.utils.RegistryManager)me.felek.floader.api.FLoader.registryManager;" +
                        "  java.lang.Integer currentViewObj = new java.lang.Integer(this.viewID);" +
                        "  if (reg.activeCustomMenusMapping.containsKey(currentViewObj)) {" +
                        "    java.lang.Integer prevView = (java.lang.Integer) reg.customMenuPreviousView.get(currentViewObj);" +
                        "    if (prevView != null) {" +
                        "       this.setMenuIDByInt(prevView.intValue());" +
                        "    } else {" +
                        "       this.setMenuID(age.of.civilizations2.jakowski.lukasz.View.eMAINMENU);" +
                        "    }" +
                        "    reg.activeCustomMenusMapping.remove(currentViewObj);" +
                        "    reg.customMenuPreviousView.remove(currentViewObj);" +
                        "    return;" +
                        "  }" +
                        "}"
        );

        CtMethod m = CtNewMethod.make(
                "public void openCustomMenu(java.lang.String menuKey) {" +
                        "  me.felek.floader.utils.UIUtils.openCustomMenu(this, $1);" +
                        "}", cc);
        cc.addMethod(m);

        CtMethod actionUp = cc.getDeclaredMethod("actionUp");
        actionUp.insertBefore(
                "{" +
                        "  if (this.keyboardMode && this.activeMenuElemeID >= 0) {" +
                        "    age.of.civilizations2.jakowski.lukasz.Button.MenuElemUI kbElem = this.keyboard.getMenuElem(this.activeMenuElemeID);" +
                        "    if (kbElem instanceof age.of.civilizations2.jakowski.lukasz.Button.ButtonM) {" +
                        "        if (((age.of.civilizations2.jakowski.lukasz.Button.ButtonM)kbElem).typeOfButton == age.of.civilizations2.jakowski.lukasz.Button.ButtonM$TypeOfButton.KEYBOARD_SAVE) {" +
                        "           me.felek.floader.utils.RegistryManager reg = (me.felek.floader.utils.RegistryManager)me.felek.floader.api.FLoader.registryManager;" +
                        "           Object activeC = reg.activeCustomMenusMapping.get(new java.lang.Integer(this.viewID));" +
                        "           if (activeC != null) {" +
                        "              ((me.felek.floader.api.ui.CustomMenu)activeC).onValueChange(this.keyboardActiveMenuElementID - 1, age.of.civilizations2.jakowski.lukasz.CFG.keybMess);" +
                        "           }" +
                        "        }" +
                        "    }" +
                        "  }" +
                        "}"
        );
    }
}