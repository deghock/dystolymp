package ru.spbu.distolymp.dto.admin.directories.towns;

import lombok.Data;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TownFilter {
    private int resultSize;
    private String containsName;
    private String belongsCountry;
    private boolean showHidden;
    private boolean showVisible;
}
