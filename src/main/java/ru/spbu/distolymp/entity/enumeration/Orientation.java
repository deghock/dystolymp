package ru.spbu.distolymp.entity.enumeration;

import java.util.Arrays;

/**
 * @author Daria Usova
 */
public enum Orientation {

    PORTRAIT("P"),
    LANDSCAPE("L");

    private final String stringRepresentation;

    Orientation(String strRepresentation) {
        this.stringRepresentation = strRepresentation;
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    public static Orientation getOrientationByString(String stringRepresentation) {
        return Arrays.stream(Orientation.values())
                .filter(o -> o.stringRepresentation.equals(stringRepresentation))
                .findAny()
                .orElse(null);
    }

}
