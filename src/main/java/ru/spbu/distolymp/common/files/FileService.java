package ru.spbu.distolymp.common.files;

/**
 * @author Daria Usova
 */
public interface FileService {
    boolean saveFile(byte[] file, String fileName);
    void deleteFile(String fileName);
    byte[] getFileWithName(String fileName);
    void replaceFile(String prevFileName, byte[] file, String newFileName);
    String getExtensionFromFileName(String fileName);
}