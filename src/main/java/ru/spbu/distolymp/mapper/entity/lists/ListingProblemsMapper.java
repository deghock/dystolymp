package ru.spbu.distolymp.mapper.entity.lists;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingProblemDto;
import ru.spbu.distolymp.entity.lists.ListingProblems;

import java.util.List;

@Mapper
public interface ListingProblemsMapper {
    ListingProblemDto toDto(ListingProblems listing);

    ListingProblems toEntity(ListingProblemDto listingNameDto);

    List<ListingProblemDto> toDtoList(List<ListingProblems> listingProblemsList);
}
