package ru.spbu.distolymp.dto.admin.directories.regions;

import lombok.Data;

@Data
public class RegionFilter {
    private int resultSize;
    private String containsName;
}
