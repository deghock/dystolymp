package ru.spbu.distolymp.common.files;

import lombok.extern.log4j.Log4j;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Daria Usova
 */
@Log4j
public class ImageServiceImpl implements ImageService {
    private final String imageDirectoryPath;

    public ImageServiceImpl(String imageDirectoryPath) {
        this.imageDirectoryPath = imageDirectoryPath;
    }

    @Override
    public boolean saveImage(byte[] image, String fileName) {
        File file = new File(imageDirectoryPath + fileName);
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(image);
        } catch (IOException e) {
            log.error("An error occurred while saving an image", e);
            return false;
        }
        return true;
    }

    @Override
    public void deleteImage(String fileName) {
        Path path = Paths.get(imageDirectoryPath + fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("Image with name " + path + " not deleted", e);
        }
    }

    @Override
    public byte[] getImageWithName(String name) {
        Path path = Paths.get(imageDirectoryPath + name);
        if (path.toFile().exists()) {
            return readImageFile(path);
        }
        return new byte[0];
    }

    private byte[] readImageFile(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("An error occurred while reading file (" + path.getFileName() + ")", e);
            return new byte[0];
        }
    }

    @Override
    public void replaceImage(String prevFileName, byte[] image, String newFileName) {
        if (prevFileName.equals(newFileName)) {
            throw new IllegalArgumentException("New file name (" + newFileName +
                    ") must be different from previous one (" + prevFileName + ")");
        }
        saveImage(image, newFileName);
        deleteImage(prevFileName);
    }

    @Override
    public String getExtensionFromImageName(String imageName) {
        String[] parsedName = imageName.split("\\.");
        if (parsedName.length == 1)
            return "";
        return "." + parsedName[parsedName.length - 1];
    }
}