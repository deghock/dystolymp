package ru.spbu.distolymp.exception.crud.education.grade;

/**
 * @author Daria Usova
 */
public class UpdateGradeException extends RuntimeException {

    public UpdateGradeException() {
    }

    public UpdateGradeException(String message) {
        super(message);
    }

    public UpdateGradeException(String message, Throwable cause) {
        super(message, cause);
    }

}
