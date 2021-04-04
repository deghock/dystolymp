package ru.spbu.distolymp.dto.entity.diploma;

import lombok.Data;
import ru.spbu.distolymp.entity.enumeration.Orientation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Daria Usova
 */
@Data
public class DiplomaTypeDto {

    private Integer id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    @Size(min = 1, max = 45)
    private String imageFileName;

    @NotNull
    @Size(min = 1, max = 150)
    private String subtitle;

    private boolean printImage;

    private Orientation orientation;

}
