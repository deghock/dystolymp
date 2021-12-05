package ru.spbu.distolymp.dto.admin.directories.towns;

import lombok.Data;
import ru.spbu.distolymp.dto.entity.education.school.SchoolNumberTitleDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeCountryDto;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TownDetailsDto {
    Long id;
    @NotNull(message = "{town.name.required}")
    @Size(max = 255, message = "{town.name.tooLong}")
    String name;
    List<SchoolNumberTitleDto> schools;
    RegionNameCodeCountryDto region;
    Long countryId;
    boolean visible;
    boolean editable;
}
