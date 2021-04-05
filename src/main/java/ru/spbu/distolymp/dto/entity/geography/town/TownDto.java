package ru.spbu.distolymp.dto.entity.geography.town;

import lombok.Data;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TownDto {

    private Long id;

    private String name;

    private boolean visible;

    private boolean editing;

}
