package ru.spbu.distolymp.common.tasks;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Vladislav Konovalov
 */
public class TestFileGenerator {
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private TestFileGenerator() {}

    public static byte[] generateParamFile() {
        StringBuilder fileContent = new StringBuilder();

        return fileContent.toString().getBytes(CHARSET);
    }
}
