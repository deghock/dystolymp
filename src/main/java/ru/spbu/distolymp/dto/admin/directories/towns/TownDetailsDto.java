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
    private Long id;
    @NotNull(message = "{town.name.required}")
    @Size(max = 255, message = "{town.name.tooLong}")
    private String name;
    private List<SchoolNumberTitleDto> schools;
    private RegionNameCodeCountryDto region;
    private Long countryId;
    private boolean visible;
    private boolean editable;
}
