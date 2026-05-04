package me.felek.floader.api.game;

public interface IGame {
    int getPlayerCivID();
    long getCivGold(int civID);
    void setCivGold(int civID, long amount);
    String getCivName(int civID);
    String getCivTag(int civID);
    int getCivRank(int civID);
    float getCivTechLevel(int civID);
    void setCivTechLevel(int civID, float level);
    int getCivIdeology(int civID);
    void setCivIdeology(int civID, int ideologyID);
    int getMilitaryExpertise(int civID);
    int getArmyExpertiseAttack(int civID);
    int getArmyExpertiseDefense(int civID);
    void setArmyExpertiseAttack(int civID, int val);
    void setArmyExpertiseDefense(int civID, int val);
    int getDiploPoints(int civID);
    int getRelation(int civA, int civB);
    void setRelation(int civA, int civB, int value);
    boolean isAlly(int civA, int civB);
    void declareWar(int attacker, int defender);
    void recruitArmy(int civID, int provinceID, int amount);
    void dropNuke(int provinceID);
    void rebuildUI();
}
