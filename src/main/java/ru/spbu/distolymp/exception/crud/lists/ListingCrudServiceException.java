package ru.spbu.distolymp.exception.crud.lists;

/**
 * @author Daria Usova
 */
public class ListingCrudServiceException extends RuntimeException {

    public ListingCrudServiceException() {
    }

    public ListingCrudServiceException(String message) {
        super(message);
    }

    public ListingCrudServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
