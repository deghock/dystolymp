package ru.spbu.distolymp.entity.enumeration;

/**
 * @author Vladislav Konovalov
 */
public enum Sex {

    male(true),
    female(false);

    private final boolean sexFlag;

    Sex(boolean sexFlag) {
        this.sexFlag = sexFlag;
    }

    public boolean toBoolean() {
        return sexFlag;
    }

    public static Sex getSex(boolean sexFlag) {
        if (sexFlag) return Sex.male;
        return Sex.female;
    }

}
