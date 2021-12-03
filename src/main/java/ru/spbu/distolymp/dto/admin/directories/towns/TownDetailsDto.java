package ru.spbu.distolymp.dto.admin.directories.towns;

import lombok.Data;
import ru.spbu.distolymp.dto.entity.education.school.SchoolNumberTitleDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeCountryDto;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TownDetailsDto {
    Long id;
    String name;
    List<SchoolNumberTitleDto> schools;
    RegionNameCodeCountryDto region;
    boolean visible;
    boolean editable;
}
