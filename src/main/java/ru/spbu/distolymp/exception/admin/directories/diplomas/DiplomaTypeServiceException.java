package ru.spbu.distolymp.exception.admin.directories.diplomas;

/**
 * @author Daria Usova
 */
public class DiplomaTypeServiceException extends RuntimeException {

    public DiplomaTypeServiceException() {
        super();
    }

    public DiplomaTypeServiceException(String message) {
        super(message);
    }

    public DiplomaTypeServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
