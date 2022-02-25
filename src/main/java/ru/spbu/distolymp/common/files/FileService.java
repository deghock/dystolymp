package ru.spbu.distolymp.common.files;

import java.util.Set;

/**
 * @author Daria Usova
 */
public interface FileService {
    boolean saveFile(byte[] file, String fileName);
    void deleteFile(String fileName);
    void deleteFiles(Set<String> fileNames);
    byte[] getFileWithName(String fileName);
    void replaceFile(String prevFileName, byte[] file, String newFileName);
}