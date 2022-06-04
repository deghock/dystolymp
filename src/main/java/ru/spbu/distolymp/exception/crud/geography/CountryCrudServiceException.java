package ru.spbu.distolymp.exception.crud.geography;

/**
 * @author Daria Usova
 */
public class CountryCrudServiceException extends RuntimeException {

    public CountryCrudServiceException() {
    }

    public CountryCrudServiceException(String message) {
        super(message);
    }

    public CountryCrudServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
