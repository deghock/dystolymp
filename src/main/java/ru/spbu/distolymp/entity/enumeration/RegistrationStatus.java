package ru.spbu.distolymp.entity.enumeration;

/**
 * @author Vladislav Konovalov
 */
public enum RegistrationStatus {

    open(true),
    close(false);

    private final boolean registrationStatusFlag;

    RegistrationStatus(boolean registrationStatusFlag) {
        this.registrationStatusFlag = registrationStatusFlag;
    }

    public boolean toBoolean() {
        return registrationStatusFlag;
    }

    public static RegistrationStatus getRegistrationStatus(boolean registrationStatusFlag) {
        if (registrationStatusFlag) return RegistrationStatus.open;
        return RegistrationStatus.close;
    }

}
