package me.felek.floader.utils.bonus;

public enum BonusType {
    //RELIGIONS&IDEOLOGIES
    TAX("ACCEPTABLE_TAXATION"),
    GOODS("MIN_GOODS"),
    INVESTMENTS("MIN_INVESTMENTS"),
    RESEARCH("RESEARCH_COST"),
    MILITARY_UPKEEP("MILITARY_UPKEEP"),
    TRIBAL("Tribal"),

    //ONLY FOR IDOLOGIES
    ADMIN_COST("ADMINISTRATION_COST"),
    ADMIN_DISTANCE("ADMINISTRATION_COST_DISTANCE"),
    ADMIN_CAPITAL("ADMINISTRATION_COST_CAPITAL"),
    INCOME_TAX("INCOME_TAXATION"),
    INCOME_PROD("INCOME_PRODUCTION"),
    MOVE_COST("COST_OF_MOVE"),
    MOVE_SAME("COST_OF_MOVE_SAME_PROVINCE"),
    RECRUIT_COST("COST_OF_RECRUIT"),
    DEFENSE("DEFENSE_BONUS"),
    REVOLUTIONARY("REVOLUTIONARY");

    public String javaFieldName;

    BonusType(String javaFieldName) {
        this.javaFieldName = javaFieldName;
    }

    public String getJavaFieldName() {
        return javaFieldName;
    }
}
