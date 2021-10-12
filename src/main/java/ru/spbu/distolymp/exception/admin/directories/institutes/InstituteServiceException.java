package ru.spbu.distolymp.exception.admin.directories.institutes;

import ru.spbu.distolymp.exception.crud.education.InstituteCrudException;

/**
 * @author Maxim Andreev
 */
public class InstituteServiceException extends InstituteCrudException {

    public InstituteServiceException() {
    }

    public InstituteServiceException(String message) {
        super(message);
    }

}
