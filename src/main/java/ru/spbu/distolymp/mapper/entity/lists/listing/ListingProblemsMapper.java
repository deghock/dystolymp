package ru.spbu.distolymp.mapper.entity.lists.listing;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingProblemDto;
import ru.spbu.distolymp.entity.lists.ListingProblems;

@Mapper
public interface ListingProblemsMapper {
    ListingProblemDto toDto(ListingProblems listing);

    ListingProblems toEntity(ListingProblemDto listingNameDto);
}
