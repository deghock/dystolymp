package ru.spbu.distolymp.exception.crud.education.grade;

/**
 * @author Daria Usova
 */
public class AddNewGradeException extends RuntimeException {

    public AddNewGradeException() {
    }

    public AddNewGradeException(String message) {
        super(message);
    }

    public AddNewGradeException(String message, Throwable cause) {
        super(message, cause);
    }

}
