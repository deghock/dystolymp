package ru.spbu.distolymp.entity.enumeration;

/**
 * @author Vladislav Konovalov
 */
public enum Residence {

    town(true),
    village(false);

    private final boolean residenceFlag;

    Residence(boolean residenceFlag) {
        this.residenceFlag = residenceFlag;
    }

    public boolean toBoolean() {
        return residenceFlag;
    }

    public static Residence getResidence(boolean residenceFlag) {
        if (residenceFlag) return Residence.town;
        return Residence.village;
    }

}
