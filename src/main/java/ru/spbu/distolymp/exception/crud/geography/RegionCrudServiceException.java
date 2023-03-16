package ru.spbu.distolymp.exception.crud.geography;

/**
 * @author Vladislav Konovalov
 */
public class RegionCrudServiceException extends RuntimeException {
    public RegionCrudServiceException() {
    }

    public RegionCrudServiceException(String message) {
        super(message);
    }

    public RegionCrudServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
