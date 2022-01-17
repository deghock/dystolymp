package ru.spbu.distolymp.exception.common;

/**
 * @author Daria Usova
 */
public class TechnicalException extends RuntimeException {
    public TechnicalException() {
    }

    public TechnicalException(Throwable cause) {
        super(cause);
    }

    public TechnicalException(String message) {
        super(message);
    }
}