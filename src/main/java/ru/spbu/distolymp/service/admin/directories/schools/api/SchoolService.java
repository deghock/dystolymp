package ru.spbu.distolymp.service.admin.directories.schools.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.schools.SchoolDetailsDto;
import ru.spbu.distolymp.dto.admin.directories.schools.SchoolFilter;
import ru.spbu.distolymp.dto.entity.education.SchoolDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownNameDto;

import java.util.List;

/**
 * @author Maxim Andreev
 */
public interface SchoolService {
    void fillShowAllSchoolsModelMap(ModelMap modelMap, int numberOfSchoolsDisplayed);

    List<RegionNameCodeDto> fillShowRegionsByCountryId(Long countryId);

    List<SchoolDto> getSchoolsBy(SchoolFilter schoolFilter);

    List<TownNameDto> fillShowTownsByRegionId(Long regionId);

    void saveOrUpdate(SchoolDto schoolDto);

    SchoolDto getSchoolDtoById(Long id);

    SchoolDetailsDto getSchoolDetailsById(Long id);

    void deleteSchoolsById(Long[] ids);

    void fillAddNewSchoolModelMap(ModelMap modelMap);

    void fillEditPage(ModelMap modelMap, Long schoolId);
}
