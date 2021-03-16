package ru.spbu.distolymp.entity.enumeration;

/**
 * @author Vladislav Konovalov
 */
public enum AbilityStatus {

    usual(true),
    limited(false);

    private final boolean abilityStatusFlag;

    AbilityStatus(boolean abilityStatusFlag) {
        this.abilityStatusFlag = abilityStatusFlag;
    }

    public boolean toBoolean() {
        return abilityStatusFlag;
    }

    public static AbilityStatus getAbility(boolean abilityStatusFlag) {
        if (abilityStatusFlag) return AbilityStatus.usual;
        return AbilityStatus.limited;
    }

}
