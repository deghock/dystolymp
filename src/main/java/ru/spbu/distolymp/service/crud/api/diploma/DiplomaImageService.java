package ru.spbu.distolymp.service.crud.api.diploma;

/**
 * @author Daria Usova
 */
public interface DiplomaImageService {

    boolean saveDiplomaTypeImage(byte[] image, String name);

    void deleteDiplomaTypeImage(String name);

    byte[] getImageWithName(String name);

}