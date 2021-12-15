package ru.spbu.distolymp.service.crud.impl.diploma;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.spbu.distolymp.service.crud.api.diploma.DiplomaImageService;

import javax.servlet.ServletContext;
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
@Service
public class DiplomaImageServiceImpl implements DiplomaImageService {

    private final String imageDirectoryPath;

    public DiplomaImageServiceImpl(@Value("${diplomas.directory}") String imageDirectoryPath,
                                   ServletContext servletContext) {
        this.imageDirectoryPath = servletContext.getRealPath(imageDirectoryPath);
    }

    @Override
    public boolean saveDiplomaTypeImage(byte[] image, String name) {
        File file = new File(imageDirectoryPath + name);

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(image);
        } catch (IOException e) {
            log.error("An error occurred while saving diploma type image", e);
            return false;
        }

        return true;
    }

    @Override
    public void deleteDiplomaTypeImage(String name) {
        Path path = Paths.get(imageDirectoryPath + name);
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("Diploma type image with name " + path + " not deleted", e);
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

}