package ru.spbu.distolymp.dto.admin.directories.towns;

import lombok.Value;
import ru.spbu.distolymp.dto.entity.education.school.SchoolNumberTitleDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCountryDto;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Value
public class TownDetailsDto {
    Long id;
    String name;
    List<SchoolNumberTitleDto> schools;
    RegionNameCountryDto region;
    boolean visible;
    boolean editable;
}
