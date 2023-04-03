package ru.spbu.distolymp.dto.admin.directories.regions;

import lombok.Data;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownNameDto;

import java.util.List;

@Data
public class RegionDetailsDto {
    private Long id;
    private String code;
    private String name;
    private List<TownNameDto> towns;
    private CountryNameDto country;
    private boolean visible;
    private boolean editing;
}
