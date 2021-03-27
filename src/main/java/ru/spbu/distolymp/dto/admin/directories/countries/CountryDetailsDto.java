package ru.spbu.distolymp.dto.admin.directories.countries;

import lombok.Data;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownNameDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daria Usova
 */
@Data
public class CountryDetailsDto {

    private Long countryId;
    private String name;
    private boolean visible;

    private List<RegionNameCodeDto> regions;
    private List<TownNameDto> towns;

    public CountryDetailsDto() {
        this.regions = new ArrayList<>();
        this.towns = new ArrayList<>();
    }

}