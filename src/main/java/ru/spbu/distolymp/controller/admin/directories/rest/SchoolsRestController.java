package ru.spbu.distolymp.controller.admin.directories.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.spbu.distolymp.dto.entity.geography.district.DistrictNameDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownNameDto;
import ru.spbu.distolymp.service.admin.directories.schools.api.SchoolService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Maxim Andreev
 */
@RequiredArgsConstructor
@RestController
public class SchoolsRestController {

    private final SchoolService schoolService;

    @GetMapping(value = "/regions/{id}", produces = "application/json")
    public List<RegionNameCodeDto> showRegionsByCountryId(@PathVariable("id") Long countryId) {
        return schoolService.fillShowRegionsByCountryId(countryId);
    }

    @GetMapping(value = "/towns/{id}", produces = "application/json")
    public List<TownNameDto> showTownsByRegionId(@PathVariable("id") Long regionId) {
        return schoolService.fillShowTownsByRegionId(regionId);
    }
}

