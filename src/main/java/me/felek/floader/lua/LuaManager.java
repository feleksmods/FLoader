package me.felek.floader.lua;

import age.of.civilizations2.jakowski.lukasz.CFG;
import me.felek.floader.FLoader;
import me.felek.floader.lua.eventSystem.EventBus;
import me.felek.floader.lua.fl.api.*;
import me.felek.floader.lua.fl.event.ShowAdvancedEvent;
import me.felek.floader.lua.fl.event.ShowBasicEvent;
import me.felek.floader.lua.fl.player.GetPlayerCiv;
import me.felek.floader.lua.fl.registry.*;
import me.felek.floader.lua.fl.res.LoadImage;
import me.felek.floader.lua.fl.unsafe.GetCFG;
import me.felek.floader.lua.fl.utils.*;
import me.felek.floader.lua.fl.world.*;
import me.felek.floader.lua.steam.*;
import me.felek.floader.lua.utils.Binder;
import me.felek.floader.utils.bonus.BonusType;
import org.apache.logging.log4j.Level;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaManager {
    public static Globals GLOBALS;

    public static void init() {
        FLoader.LOGGER.log(Level.INFO, "Initializing LuaManager");

        GLOBALS = JsePlatform.standardGlobals();

        LuaValue fl = LuaValue.tableOf();

        LuaValue playerModule = LuaValue.tableOf();
        LuaValue worldModule =  LuaValue.tableOf();
        LuaValue utilsModule = LuaValue.tableOf();
        LuaValue registryModule = LuaValue.tableOf();
        LuaValue unsafeModule = LuaValue.tableOf();
        LuaValue resModule = LuaValue.tableOf();
        LuaValue eventModule = LuaValue.tableOf();
        LuaValue bonus = LuaValue.tableOf();
        LuaValue settingsModule = LuaValue.tableOf();
        LuaValue api = LuaValue.tableOf();
        LuaValue keys = LuaValue.tableOf();
        LuaValue steam = LuaValue.tableOf();

        GenerateKeys.generate(keys);

        api.set("getModsList", new GetModsList());
        api.set("getModInfo", new GetModInfo());
        api.set("getLoaderVersion", new GetLoaderVersion());
        api.set("getLoaderName", new GetLoaderName());

        steam.set("grantAchievement", new GrantSteamAchievement());
        steam.set("setRichPresence", new SetRichPresence());
        steam.set("getPlaytime", new GetPlayTime());
        steam.set("getSteamName", new GetSteamName());
        steam.set("openSteamUrl", new OpenSteamUrl());
        steam.set("openSteamOverlay", new OpenSteamUrl());

        Binder.bind(settingsModule, "FogOfWar", CFG.class, "FOG_OF_WAR");
        Binder.bind(settingsModule, "SandBoxMode", CFG.class, "SANDBOX_MODE");
        Binder.bind(settingsModule, "GuiScale", CFG.class, "GUI_SCALE");
        Binder.bind(settingsModule, "TotalWarMode", CFG.class, "TOTAL_WARMODE");
        Binder.bind(settingsModule, "LeadersCanDie", CFG.class, "LEADERS_CAN_DIE");
        Binder.bind(settingsModule, "AgeOfChaos", CFG.class, "AGE_OF_CHAOS_MODE");
        Binder.bind(settingsModule, "AgeOfChaosTurns", CFG.class, "AGE_OF_CHAOS_TURNS");
        Binder.bind(settingsModule, "GameWidth", CFG.class, "GAMEWIDTH");
        Binder.bind(settingsModule, "GameHeight", CFG.class, "GAMEHEIGHT");
        Binder.bind(settingsModule, "ButtonH", CFG.class, "BUTTON_H");
        Binder.bind(settingsModule, "ButtonW", CFG.class, "BUTTON_W");
        Binder.bind(settingsModule, "Padding", CFG.class, "PADD");
        Binder.bind(settingsModule, "Density", CFG.class, "DENSITY");

        for (BonusType type : BonusType.values())
            bonus.set(type.name(), LuaValue.valueOf(type.name()));

        eventModule.set("showEvent", new ShowBasicEvent());
        eventModule.set("showAdvancedEvent", new ShowAdvancedEvent());

        resModule.set("loadImage", new LoadImage());

        unsafeModule.set("getCFG", new GetCFG());

        registryModule.set("registerCustomLoadingTip", new AddCustomLoadingTip());
        registryModule.set("registerCommand", new RegisterCommand());
        registryModule.set("registerCustomLoadingScreen", new RegisterCustomLoadingScreen());
        registryModule.set("registerReligion", new RegisterCustomReligion());
        registryModule.set("registerIdeology", new RegisterIdeology());
        registryModule.set("registerBind", new RegisterKeybind());

        playerModule.set("getPlayerCiv", new GetPlayerCiv());

        Binder.bind(worldModule, "PopulationGrowthRate", CFG.class, "POPULATION_GROWTH_RATE");
        Binder.bind(worldModule, "EconomyGrowthRate", CFG.class, "ECONOMY_GROWTH_RATE");
        Binder.bind(worldModule, "AssimilationSpeedModifier", CFG.class, "ASSIMILATION_SPEED_MODIFIER");
        Binder.bind(worldModule, "AssimilationCostModifier", CFG.class, "ASSIMILATION_COST_MODIFIER");
        Binder.bind(worldModule, "PeaceTreatyVictoryPointsModifier", CFG.class, "PEACE_TREATY_VICTORY_POINTS_MODIFIER");
        Binder.bind(worldModule, "RebelsPower", CFG.class, "REBELS_POWER");
        Binder.bind(worldModule, "PlunderModifier", CFG.class, "PLUNDER_MODIFIER");
        Binder.bind(worldModule, "MovementPointsMaxModifier", CFG.class, "MOVEMENT_POINTS_MAX_MODIFIER");

        worldModule.set("getCivMoney", new GetCivMoney());
        worldModule.set("setCivMoney", new SetCivMoney());
        worldModule.set("getCivName", new GetCivName());
        worldModule.set("getCivTag", new GetCivTag());
        worldModule.set("getProvincesCount", new GetProvincesCount());
        worldModule.set("getCapitalId", new GetCapitalId());
        worldModule.set("getCivRank", new GetCivRank());
        worldModule.set("getCivTechLevel", new GetCivTechLevel());
        worldModule.set("getDiploPoints", new GetDiploPoints());
        worldModule.set("getProvinceOwner", new GetProvinceOwner());
        worldModule.set("isCapital", new IsCapital());
        worldModule.set("setProvinceOwner", new SetProvinceOwner());
        worldModule.set("getProvinceStability", new GetProvinceStability());
        worldModule.set("getProvinceHappiness", new GetProvinceHappiness());
        worldModule.set("getProvinceArmy", new GetProvinceArmy());
        worldModule.set("getProvinceEconomy", new GetProvinceEconomy());
        worldModule.set("getProvincePopulation", new GetProvincePopulation());
        worldModule.set("isAlly", new IsAlly());
        worldModule.set("getTruceTurns", new GetTruceTurns());
        worldModule.set("declareWar", new DeclareWar());
        worldModule.set("getCivsAtWar", new GetCivsAtWar());
        worldModule.set("setRelation", new SetRelation());
        worldModule.set("getRelation", new GetRelation());
        worldModule.set("setArmyExpertiseDefense", new SetArmyExpertiseDefense());
        worldModule.set("setArmyExpertiseAttack", new SetArmyExpertiseAttack());
        worldModule.set("getArmyExpertiseDefence", new GetArmyExpertiseDefense());
        worldModule.set("getArmyExpertiseAttack", new GetArmyExpertiseAttack());
        worldModule.set("getMilitaryExpertise", new GetMilitaryExpertise());
        worldModule.set("getCivCasualties", new GetCivCasualties());
        worldModule.set("recruitArmy", new RecruitArmy());
        worldModule.set("setCivIdeology", new SetCivIdeology());
        worldModule.set("getCivIdeology", new GetCivIdeology());
        worldModule.set("getMovementPoints", new GetMovementPoints());
        worldModule.set("createMission", new CreateMission());
        worldModule.set("dropNuke", new DropNuke());
        worldModule.set("setCivNukes", new SetCivNukes());

        utilsModule.set("getMonthName", new GetMonthName());
        utilsModule.set("log", new Log());
        utilsModule.set("getTurnID", new GetTurnID());
        utilsModule.set("reloadFL", new ReloadFL());

        GLOBALS.set("subscribe", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
                EventBus.subscribe(luaValue.tojstring(), (LuaFunction) luaValue1);
                FLoader.LOGGER.info("Registered new " + luaValue.tojstring() + " event");
                return NIL;
            }
        });

        fl.set("player", playerModule);
        fl.set("world", worldModule);
        fl.set("utils", utilsModule);
        fl.set("registry", registryModule);
        fl.set("unsafe", unsafeModule);
        fl.set("res", resModule);
        fl.set("event", eventModule);
        fl.set("bonus", bonus);
        fl.set("keys", keys);
        fl.set("api", api);
        fl.set("settings", settingsModule);
        fl.set("steam", steam);
        GLOBALS.set("fl", fl);
    }
}
