package ru.spbu.distolymp.dto.entity.education;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Daria Usova
 */
@Data
public class PlaceDto {

    private Long id;
    private Long divisionId;
    private boolean visible;

    @Size(min = 1, max = 65535)
    @NotNull(message = "{place.name.required}")
    private String name;

    private Integer order;

}
