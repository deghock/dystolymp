package ru.spbu.distolymp.dto.entity.lists.listing;

import lombok.Data;
import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;

@Data
public class ListingProblemDto {
    private long id;
    private int order;
    private ListingNameDto listing;
    private ProblemDto problem;
}
