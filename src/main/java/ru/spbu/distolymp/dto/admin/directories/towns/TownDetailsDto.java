package ru.spbu.distolymp.dto.admin.directories.towns;

import lombok.Data;
import ru.spbu.distolymp.dto.entity.education.school.SchoolNumberTitleDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TownDetailsDto {
    private Long id;

    private String name;

    private List<SchoolNumberTitleDto> schools;

    private boolean visible;

    private boolean editing;

    public TownDetailsDto() {
        this.schools = new ArrayList<>();
    }
}
