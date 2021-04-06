package ru.spbu.distolymp.exception.crud.education.grade;

/**
 * @author Daria Usova
 */
public class DeleteGradeException extends RuntimeException {

    public DeleteGradeException() {
    }

    public DeleteGradeException(String message) {
        super(message);
    }

    public DeleteGradeException(String message, Throwable cause) {
        super(message, cause);
    }

}
