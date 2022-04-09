package ru.spbu.distolymp.dto.admin.tests;

import lombok.Data;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TestPreviewDto {
    private Long id;
    private String previewText;
    private String testFolder;
    private String imageName;
    private Integer width;
    private Integer height;
    private String currentServerDateTime;
}
