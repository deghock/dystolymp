package ru.spbu.distolymp.dto.entity.education.grade;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
public class GradeNameDto {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

}
