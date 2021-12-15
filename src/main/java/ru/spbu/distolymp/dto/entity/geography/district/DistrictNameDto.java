package ru.spbu.distolymp.dto.entity.geography.district;

import lombok.Value;

/**
 * @author Maxim Andreev
 */
@Value
public class DistrictNameDto {
    /**
     * District id
     */
    Long id;

    /**
     * District name
     */
    String name;

}
