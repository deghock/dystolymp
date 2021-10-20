package ru.spbu.distolymp.dto.admin.directories.schools;

import lombok.Data;
import ru.spbu.distolymp.entity.education.SchoolType;


/**
 * @author Maxim Andreev
 */
@Data
public class SchoolDetailsDto {

    private Long schoolId;
    private Long townId;
    private boolean editing;
    private boolean visible;
    private Integer number;
    private String title;
    private String address;

    private String countryName;
    private String regionName;
    private String townName;
    private String typeName;

}
