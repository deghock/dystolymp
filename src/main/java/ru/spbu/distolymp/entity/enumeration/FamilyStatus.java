package ru.spbu.distolymp.entity.enumeration;

/**
 * @author Vladislav Konovalov
 */
public enum FamilyStatus {

    usual(true),
    orphan(false);

    private final boolean familyStatusFlag;

    FamilyStatus(boolean familyStatusFlag) {
        this.familyStatusFlag = familyStatusFlag;
    }

    public boolean toBoolean() {
        return familyStatusFlag;
    }

    public static FamilyStatus getFamilyStatus(boolean familyStatusFlag) {
        if (familyStatusFlag) return FamilyStatus.usual;
        return FamilyStatus.orphan;
    }

}
