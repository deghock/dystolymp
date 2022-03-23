package ru.spbu.distolymp.common.files;

import lombok.extern.log4j.Log4j;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * @author Daria Usova, Vladislav Konovalov
 */
@Log4j
public class FileServiceImpl implements FileService {
    private final String fileDirectoryPath;

    public FileServiceImpl(String fileDirectoryPath) {
        this.fileDirectoryPath = fileDirectoryPath;
    }

    @Override
    public boolean saveFile(byte[] file, String fileName) {
        File fileLocation = new File(fileDirectoryPath + fileName);
        try (OutputStream os = new FileOutputStream(fileLocation)) {
            os.write(file);
        } catch (IOException e) {
            log.error("An error occurred while saving a file", e);
            return false;
        }
        return true;
    }

    @Override
    public void deleteFile(String fileName) {
        Path path = Paths.get(fileDirectoryPath + fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("File with name " + path.getFileName() + " not deleted", e);
        }
    }

    @Override
    public void deleteFiles(Set<String> fileNames) {
        for (String fileName : fileNames)
            deleteFile(fileName);
    }

    @Override
    public byte[] getFileWithName(String fileName) {
        Path path = Paths.get(fileDirectoryPath + fileName);
        if (path.toFile().exists()) {
            return readFile(path);
        }
        return new byte[0];
    }

    private byte[] readFile(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("An error occurred while reading file (" + path.getFileName() + ")", e);
            return new byte[0];
        }
    }

    @Override
    public void replaceFile(String prevFileName, byte[] file, String newFileName) {
        if (prevFileName.equals(newFileName)) {
            throw new IllegalArgumentException("New file name (" + newFileName +
                    ") must be different from previous one (" + prevFileName + ")");
        }
        this.saveFile(file, newFileName);
        this.deleteFile(prevFileName);
    }

    @Override
    public boolean createDirectory(String dirName) {
        Path dirPath = Paths.get(fileDirectoryPath + dirName);
        try {
            Files.createDirectory(dirPath);
            return true;
        } catch (IOException e) {
            log.error("An error occurred while creating a directory", e);
            return false;
        }
    }
}