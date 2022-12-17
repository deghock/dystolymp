package ru.spbu.distolymp.dto.entity.lists.listing;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */

@Data
public class ListingNameDto {
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;
}
