package ru.spbu.distolymp.dto.admin.directories.schools;

import lombok.Data;

/**
 * @author Maxim Andreev
 */
@Data
public class SchoolFilter {
    private String title;
    private Integer number;
    private Long countryId;
    private Long regionId;
    private Long townId;
    //private Long districtId;
    private int resultSize;
    //private boolean showVisible;
    //private boolean showHidden;
}
