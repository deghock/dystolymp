package ru.spbu.distolymp.exception.crud.lists.category;

/**
 * @author Daria Usova
 */
public class DeleteCategoryException extends RuntimeException {

    public DeleteCategoryException() {
    }

    public DeleteCategoryException(String message) {
        super(message);
    }

    public DeleteCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

}
