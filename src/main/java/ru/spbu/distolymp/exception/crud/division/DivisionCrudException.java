package ru.spbu.distolymp.exception.crud.division;

/**
 * @author Daria Usova
 */
public class DivisionCrudException extends RuntimeException {

    public DivisionCrudException() {
    }

    public DivisionCrudException(String message) {
        super(message);
    }

    public DivisionCrudException(String message, Throwable cause) {
        super(message, cause);
    }

}