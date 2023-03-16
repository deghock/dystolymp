package ru.spbu.distolymp.dto.entity.education;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Maxim Andreev
 */
@Data
public class SchoolTypeDto {
    private Long id;
    private boolean visible;
    private String name;

}
