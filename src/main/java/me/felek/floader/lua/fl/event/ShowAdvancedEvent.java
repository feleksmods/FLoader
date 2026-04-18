package me.felek.floader.lua.fl.event;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Event_Decision;
import age.of.civilizations2.jakowski.lukasz.Event_GameData;
import age.of.civilizations2.jakowski.lukasz.Menus.ZRest.Menu_InGame_Event;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowAdvancedEvent extends VarArgFunction {
    @Override
    public Varargs invoke(Varargs args) {
        int civID = args.arg(1).optint(0);
        String title = args.arg(2).optjstring("Event");
        String description = args.arg(3).optjstring("");
        String imgKey = args.arg(4).optjstring("default.png");

        Event_GameData dynamicEvent = new Event_GameData();
        if (dynamicEvent.getEventTag() == null || dynamicEvent.getEventTag().length() == 0) {
            dynamicEvent.setEventTag("LUA_EVT_" + System.currentTimeMillis());
        }

        dynamicEvent.setEventName(title);
        dynamicEvent.getEvent_PopUp().sText = description;
        dynamicEvent.setEventPicture(imgKey);
        dynamicEvent.setCivID(civID);

        for (int i = 5; i <= args.narg(); i += 2) {
            String btnText = args.arg(i).optjstring("Option");
            LuaFunction btnFunc = args.arg(i + 1).checkfunction();
            Event_Decision decision = new Event_Decision();
            decision.sTitle = btnText;

            EventLuaOutcome luaAction = new EventLuaOutcome(btnFunc);
            decision.lOutcomes.add(luaAction);

            dynamicEvent.lDecisions.add(decision);
        }

        CFG.eventsManager.addEvent(dynamicEvent);
        Menu_InGame_Event.EVENT_ID = CFG.eventsManager.getEventsSize() - 1;
        CFG.menus.rebuildInGame_Event();
        CFG.menus.setVisibleInGame_Event(true);

        return NIL;
    }
}
