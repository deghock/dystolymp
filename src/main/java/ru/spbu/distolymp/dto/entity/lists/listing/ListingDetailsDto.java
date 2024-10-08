package ru.spbu.distolymp.dto.entity.lists.listing;

import lombok.Data;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Data
public class ListingDetailsDto {
    private Long id;
    private String name;
    private ConstraintDto constraint;
    private List<ListingProblemDto> problemList;
}
