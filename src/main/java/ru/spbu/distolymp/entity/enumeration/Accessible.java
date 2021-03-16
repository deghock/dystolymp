package ru.spbu.distolymp.entity.enumeration;

/**
 * @author Vladislav Konovalov
 */
public enum Accessible {

    yes(true),
    no(false);

    private final boolean accessibleFlag;

    Accessible(boolean accessibleFlag) {
        this.accessibleFlag = accessibleFlag;
    }

    public boolean toBoolean() {
        return accessibleFlag;
    }

    public static Accessible getAccessible(boolean accessibleFlag) {
        if (accessibleFlag) return Accessible.yes;
        return Accessible.no;
    }

}
