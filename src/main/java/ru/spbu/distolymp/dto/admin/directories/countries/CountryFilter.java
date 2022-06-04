package ru.spbu.distolymp.dto.admin.directories.countries;

import lombok.Data;

/**
 * @author Daria Usova
 */
@Data
public class CountryFilter {

    private String containsName;
    private int resultSize;
    private boolean showVisible;
    private boolean showHidden;

}