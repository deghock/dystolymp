package ru.spbu.distolymp.dto.entity.lists;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Daria Usova
 */
@Data
public class CategoryDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    private Long divisionId;

}
