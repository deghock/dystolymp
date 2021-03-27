package ru.spbu.distolymp.dto.entity.geography.country;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Daria Usova
 */
@Data
public class CountryDto {

    private Long id;

    @NotNull(message = "{country.name.required}")
    @Size(min = 1, max = 255, message = "{country.name.length}")
    private String name;

    private boolean visible;
    private boolean editing;

}
