package ru.spbu.distolymp.dto.admin.models;

import lombok.Data;

/**
 * @author Vladislav Konovalov
 */
@Data
public class ModelListDto {
    private Long id;
    private String prefix;
    private String title;
    private String status;
}
