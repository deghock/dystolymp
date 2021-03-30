package ru.spbu.distolymp.dto.entity.geography.town;

import lombok.Value;

/**
 * @author Daria Usova
 */
@Value
public class TownNameDto {

    /**
     * Town id
     */
    Long id;

    /**
     * Town name
     */
    String name;

}
