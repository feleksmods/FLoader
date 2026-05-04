package me.felek.floader.utils.impl;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.GameManager;
import age.of.civilizations2.jakowski.lukasz.Render;
import me.felek.floader.api.game.IGame;

public class GameImpl implements IGame {
    @Override
    public int getPlayerCivID() {
        return CFG.core.getPlayer(CFG.PLAYER_TURN_ID).getCivId();
    }

    @Override
    public long getCivGold(int civID) {
        return CFG.core.getCiv(civID).getGold();
    }

    @Override
    public void setCivGold(int civID, long amount) {
        CFG.core.getCiv(civID).setGold(amount);
    }

    @Override
    public String getCivName(int civID) {
        return CFG.core.getCiv(civID).getCivName();
    }

    @Override
    public String getCivTag(int civID) {
        return CFG.core.getCiv(civID).getCivTag();
    }

    @Override
    public int getCivRank(int civID) {
        return CFG.core.getCiv(civID).getRankPos();
    }

    @Override
    public float getCivTechLevel(int civID) {
        return CFG.core.getCiv(civID).getTechLevel();
    }

    @Override
    public void setCivTechLevel(int civID, float level) {
        CFG.core.getCiv(civID).setTechLevel(level);
    }

    @Override
    public int getCivIdeology(int civID) {
        return CFG.core.getCiv(civID).getIdeology();
    }

    @Override
    public void setCivIdeology(int civID, int ideologyID) {
        CFG.core.getCiv(civID).setIdeology(ideologyID);
    }

    @Override
    public int getMilitaryExpertise(int civID) {
        return CFG.core.getCiv(civID).civGD.armyExpertiseLevel;
    }

    @Override
    public int getArmyExpertiseAttack(int civID) {
        return CFG.core.getCiv(civID).civGD.armyExpertiseAttack;
    }

    @Override
    public int getArmyExpertiseDefense(int civID) {
        return CFG.core.getCiv(civID).civGD.armyExpertiseDefense;
    }

    @Override
    public void setArmyExpertiseAttack(int civID, int val) {
        CFG.core.getCiv(civID).civGD.armyExpertiseAttack = val;
    }

    @Override
    public void setArmyExpertiseDefense(int civID, int val) {
        CFG.core.getCiv(civID).civGD.armyExpertiseDefense = val;
    }

    @Override
    public int getDiploPoints(int civID) {
        return CFG.core.getCiv(civID).getDiploPoints();
    }

    @Override
    public int getRelation(int civA, int civB) {
        return (int) CFG.core.getCivRelationOfCivB(civA, civB);
    }

    @Override
    public void setRelation(int civA, int civB, int value) {
        CFG.core.setCivRelationOfCivB(civA, civB, value);
    }

    @Override
    public boolean isAlly(int civA, int civB) {
        return CFG.core.isAlly(civA, civB);
    }

    @Override
    public void declareWar(int attacker, int defender) {
        CFG.core.declareWar(attacker, defender, true);
    }

    @Override
    public void recruitArmy(int civID, int provinceID, int amount) {
        CFG.core.getCiv(civID).recruitArmy(provinceID, amount);
    }

    @Override
    public void dropNuke(int provinceID) {
        GameManager.sendNukes(1, getPlayerCivID(), provinceID);
    }

    @Override
    public void rebuildUI() {
        Render.updateRenderer();
    }
}
