package ru.spbu.distolymp.entity.enumeration;

/**
 * @author Vladislav Konovalov
 */
public enum Correctness {
    Y("Правильно"),
    N("Неправильно"),
    P("Частично"),
    U("Непроверено"),
    C("Отменено");

    private final String stringValue;

    Correctness(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public String getSelfColor() {
        switch (this) {
            case Y:
                return "#28A745";
            case N:
            case C:
                return "#DC3545";
            default:
                return "#212529";
        }
    }
}
