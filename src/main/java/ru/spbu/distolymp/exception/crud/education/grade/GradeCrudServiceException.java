package ru.spbu.distolymp.exception.crud.education.grade;

/**
 * @author Daria Usova
 */
public class GradeCrudServiceException extends RuntimeException {

    public GradeCrudServiceException() {
    }

    public GradeCrudServiceException(String message) {
        super(message);
    }

    public GradeCrudServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}