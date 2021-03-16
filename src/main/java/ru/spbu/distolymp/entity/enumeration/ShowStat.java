package ru.spbu.distolymp.entity.enumeration;

/**
 * @author Vladislav Konovalov
 */
public enum ShowStat {

    yes(true),
    no(false);

    private final boolean showStatFlag;

    ShowStat(boolean showStatFlag) {
        this.showStatFlag = showStatFlag;
    }

    public boolean toBoolean() {
        return showStatFlag;
    }

    public static ShowStat getShowStat(boolean showStatFlag) {
        if (showStatFlag) return ShowStat.yes;
        return ShowStat.no;
    }

}
