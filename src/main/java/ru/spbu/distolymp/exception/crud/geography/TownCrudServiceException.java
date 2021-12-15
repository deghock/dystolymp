package ru.spbu.distolymp.exception.crud.geography;

/**
 * @author Vladislav Konovalov
 */
public class TownCrudServiceException extends RuntimeException {
    public TownCrudServiceException() {
    }

    public TownCrudServiceException(String message) {
        super(message);
    }

    public TownCrudServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
