package ru.spbu.distolymp.entity.enumeration;

/**
 * @author Vladislav Konovalov
 */
public enum GroupAccess {

    single(true),
    multiple(false);

    private final boolean groupAccessFlag;

    GroupAccess(boolean groupAccessFlag) {
        this.groupAccessFlag = groupAccessFlag;
    }

    public boolean toBoolean() {
        return groupAccessFlag;
    }

    public static GroupAccess getGroupAccess(boolean groupAccessFlag) {
        if (groupAccessFlag) return GroupAccess.single;
        return GroupAccess.multiple;
    }

}
