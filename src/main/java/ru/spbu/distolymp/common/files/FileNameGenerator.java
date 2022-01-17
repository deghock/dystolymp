package ru.spbu.distolymp.common.files;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Daria Usova
 */
public class FileNameGenerator {
    private FileNameGenerator() {}

    public static String generateFileName(String extension) {
        return generateFileName() + extension;
    }

    private static String generateFileName() {
        StringBuilder stringBuilder = new StringBuilder();
        int aCharIndex = 97;
        int zCharIndex = 122;

        for (int i = 1; i <= 20; i++) {
            stringBuilder.append((char) ThreadLocalRandom.current().nextInt(aCharIndex, zCharIndex + 1));
        }

        return stringBuilder.toString();
    }
}