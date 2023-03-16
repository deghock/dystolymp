package ru.spbu.distolymp.dto.entity.geography.region;

import lombok.Data;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;

/**
 * @author Vladislav Konovalov
 */
@Data
public class RegionNameCodeCountryDto {
    private Long id;
    private String name;
    private String code;
    private CountryNameDto country;
}
