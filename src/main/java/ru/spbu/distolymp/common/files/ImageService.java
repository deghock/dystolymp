package ru.spbu.distolymp.common.files;

/**
 * @author Daria Usova
 */
public interface ImageService {
    boolean saveImage(byte[] image, String fileName);
    void deleteImage(String fileName);
    byte[] getImageWithName(String name);
    void replaceImage(String prevFileName, byte[] image, String newFileName);
}