package ru.spbu.distolymp.common.files;

import java.util.UUID;

/**
 * @author Daria Usova, Vladislav Konovalov
 */
public class FileNameGenerator {
    private FileNameGenerator() {}

    public static String generateFileName(String extension) {
        return generateFileName() + extension;
    }

    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }
}