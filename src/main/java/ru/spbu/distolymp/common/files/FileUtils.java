package ru.spbu.distolymp.common.files;

import lombok.extern.log4j.Log4j;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.exception.common.TechnicalException;
import java.io.IOException;

/**
 * @author Daria Usova
 */
@Log4j
public class FileUtils {
    private FileUtils() {}

    public static String getImageExtension(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.getContentType() == null) {
            throw new IllegalArgumentException("Multipart file and content must not be null");
        } else {
            return "." + multipartFile.getContentType().replaceFirst("image/", "");
        }
    }

    public static byte[] getFileBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            log.error("An error occurred while getting bytes of a multipart file", e);
            throw new TechnicalException(e);
        }
    }

    public static String getExtensionFromFileName(String fileName) {
        String[] parsedName = fileName.split("\\.");
        if (parsedName.length == 1)
            return "";
        return "." + parsedName[parsedName.length - 1];
    }
}