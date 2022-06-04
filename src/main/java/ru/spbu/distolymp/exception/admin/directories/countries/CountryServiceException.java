package ru.spbu.distolymp.exception.admin.directories.countries;

/**
 * @author Daria Usova
 */
public class CountryServiceException extends RuntimeException {

    public CountryServiceException() {
        super();
    }

    public CountryServiceException(String message) {
        super(message);
    }

    public CountryServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
