package ru.spbu.distolymp.dto.admin.directories.grades;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
public class GradeNameDto {

    private Long id;

    @Size(max = 50)
    @NotNull
    private String name;

    @NotNull
    private Long divisionId;

}
