package me.felek.floader.lua.fl.event;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Event_Decision;
import age.of.civilizations2.jakowski.lukasz.Event_GameData;
import age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class ShowBasicEvent extends VarArgFunction {
    @Override
    public Varargs invoke(Varargs args) {
        int civID = args.arg(1).optint(0);
        String title = args.arg(2).optjstring("Event");
        String description = args.arg(3).optjstring("");
        String imgKey = args.arg(4).optjstring("default.png");

        Event_GameData dynamicEvent = new Event_GameData();
        dynamicEvent.setEventName(title);
        dynamicEvent.getEvent_PopUp().sText = description;
        dynamicEvent.setEventPicture(imgKey);
        dynamicEvent.setCivID(civID);

        Event_Decision okButton = new Event_Decision();
        okButton.sTitle = "OK";
        dynamicEvent.lDecisions.add(okButton);

        CFG.eventsManager.addEvent(dynamicEvent);
        int lastEIdx = CFG.eventsManager.getEventsSize() - 1;

        Menu_InGame_Event.EVENT_ID = lastEIdx;
        CFG.menus.rebuildInGame_Event();

        CFG.menus.setVisibleInGame_Event(true);

        return NIL;
    }
}
