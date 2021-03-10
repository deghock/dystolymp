package ru.spbu.distolymp.entity.enumeration;

/**
 * @author Daria Usova
 */
public enum Visible {

    yes(true),
    no(false);

    private final boolean visibleFlag;

    Visible(boolean visibleFlag) {
        this.visibleFlag = visibleFlag;
    }

    public boolean toBoolean() {
        return visibleFlag;
    }

    public static Visible getVisible(boolean visibleFlag) {
        if (visibleFlag) return Visible.yes;
        return Visible.no;
    }

}
