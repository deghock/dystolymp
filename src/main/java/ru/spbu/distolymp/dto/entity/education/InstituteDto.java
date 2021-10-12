package ru.spbu.distolymp.dto.entity.education;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author Maxim Andreev
 */
@Data
public class InstituteDto {

    private Long id;

    @Size(min = 1, max = 65535)
    @NotNull(message = "{institute.name.required}")
    private String name;

    private Integer order;

}
