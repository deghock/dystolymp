package ru.spbu.distolymp.dto.entity.lists.listing;

import lombok.Data;
import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;

@Data
public class ListingProblemDto {
    private Long id;
    private Integer order;
    private ProblemDto problem;
}
