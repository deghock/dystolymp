package ru.spbu.distolymp.exception.admin.directories.schools;

import ru.spbu.distolymp.exception.crud.education.SchoolCrudException;

/**
 * @author Maxim Andreev
 */
public class SchoolServiceException extends SchoolCrudException {

    public SchoolServiceException() {
    }

    public SchoolServiceException(String message) {
        super(message);
    }

}
