package ru.spbu.distolymp.exception.crud.education;

/**
 * @author Maxim Andreev
 */
public class SchoolCrudException extends RuntimeException {

    public SchoolCrudException() {
    }

    public SchoolCrudException(String message) {
        super(message);
    }

    public SchoolCrudException(String message, Throwable cause) {
        super(message, cause);
    }

}
