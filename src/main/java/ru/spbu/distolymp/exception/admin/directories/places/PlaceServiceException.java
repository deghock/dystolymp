package ru.spbu.distolymp.exception.admin.directories.places;

import ru.spbu.distolymp.exception.crud.education.PlaceCrudException;

/**
 * @author Daria Usova
 */
public class PlaceServiceException extends PlaceCrudException {

    public PlaceServiceException() {
    }

    public PlaceServiceException(String message) {
        super(message);
    }

}
