package ru.spbu.distolymp.exception.crud.tasks;

/**
 * @author Vladislav Konovalov
 */
public class TaskCrudServiceException extends RuntimeException {
    public TaskCrudServiceException() {}

    public TaskCrudServiceException(String message) {
        super(message);
    }

    public TaskCrudServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
