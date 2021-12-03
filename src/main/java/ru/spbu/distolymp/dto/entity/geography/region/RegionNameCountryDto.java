package ru.spbu.distolymp.dto.entity.geography.region;

import lombok.Value;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;

/**
 * @author Vladislav Konovalov
 */
@Value
public class RegionNameCountryDto {
    Long id;
    String name;
    CountryNameDto country;
}
