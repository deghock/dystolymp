package ru.spbu.distolymp.dto.entity.education;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Maxim Andreev
 */
@Data
public class SchoolDto {

    private Long id;
    private Long divisionId;
    private Long typeId;
    private Long townId;
    private Long districtId;

    private boolean editing;
    
    private boolean visible;

    @NotNull(message = "{school.number.required}")
    private Integer number;

    @NotNull(message = "{school.title.required}")
    @Size(min = 1, max = 255, message = "{school.title.length}")
    private String title;

    @NotNull(message = "{school.address.required}")
    private String address;

}
