package ru.spbu.distolymp.exception.crud.diploma;

/**
 * @author Daria Usova
 */
public class DiplomaTypeCrudServiceException extends RuntimeException {

    public DiplomaTypeCrudServiceException() {
        super();
    }

    public DiplomaTypeCrudServiceException(String message) {
        super(message);
    }

    public DiplomaTypeCrudServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
