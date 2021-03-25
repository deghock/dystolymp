package ru.spbu.distolymp.exception.crud.users.staff;

/**
 * @author Vladislav Konovalov
 */
public class StaffCrudServiceException extends RuntimeException {

    public StaffCrudServiceException() {
        super();
    }

    public StaffCrudServiceException(String message) {
        super(message);
    }

}
