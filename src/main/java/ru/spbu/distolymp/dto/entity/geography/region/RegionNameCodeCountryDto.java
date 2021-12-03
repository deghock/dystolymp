package ru.spbu.distolymp.dto.entity.geography.region;

import lombok.Value;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;

/**
 * @author Vladislav Konovalov
 */
@Value
public class RegionNameCodeCountryDto {
    Long id;
    String name;
    String code;
    CountryNameDto country;
}
