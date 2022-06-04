package ru.spbu.distolymp.dto.entity.geography.region;

import lombok.Value;

/**
 * @author Daria Usova
 */
@Value
public class RegionNameCodeDto {

    /**
     * Region id
     */
    Long id;

    /**
     * Region name
     */
    String name;

    /**
     * Region code
     */
    String code;

}
