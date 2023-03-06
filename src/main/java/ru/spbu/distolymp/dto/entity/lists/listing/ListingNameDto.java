package ru.spbu.distolymp.dto.entity.lists.listing;

import lombok.Data;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Data
public class ListingNameDto {

    private Long id;
    private String name;
    private List<ListingProblemDto> problemList;
}
