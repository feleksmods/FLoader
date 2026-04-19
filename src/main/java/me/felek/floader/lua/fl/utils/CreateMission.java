package me.felek.floader.lua.fl.utils;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Event_GameData;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

public class CreateMission extends VarArgFunction {
    @Override
    public Varargs invoke(Varargs args) {
        String title = args.arg(1).checkjstring();
        String desc = args.arg(2).checkjstring();
        int civID = args.arg(3).checkint();

        Event_GameData mission = new Event_GameData();
        mission.setEventName(title);
        mission.getEvent_PopUp().sText = desc;
        mission.setCivID(civID);
        mission.isMission = true;

        CFG.eventsManager.addEvent(mission);
        return LuaValue.valueOf(CFG.eventsManager.getEventsSize() - 1);
    }
}
