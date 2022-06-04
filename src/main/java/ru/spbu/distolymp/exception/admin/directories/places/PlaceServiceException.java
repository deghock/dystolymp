package ru.spbu.distolymp.exception.admin.directories.places;

import ru.spbu.distolymp.exception.crud.education.PlaceCrudServiceException;

/**
 * @author Daria Usova
 */
public class PlaceServiceException extends PlaceCrudServiceException {

    public PlaceServiceException() {
    }

    public PlaceServiceException(String message) {
        super(message);
    }

}
