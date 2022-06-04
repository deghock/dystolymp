package ru.spbu.distolymp.exception.crud.education.grade;

/**
 * @author Daria Usova
 */
public class RenameGradeException extends RuntimeException {

    public RenameGradeException() {
    }

    public RenameGradeException(String message) {
        super(message);
    }

    public RenameGradeException(String message, Throwable cause) {
        super(message, cause);
    }

}
