package ru.spbu.distolymp.mapper.entity.lists;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingDetailsDto;
import ru.spbu.distolymp.entity.lists.Listing;

import java.util.List;

@Mapper
public interface ListingDetailsMapper {
    List<ListingDetailsDto> toDtoList(List<Listing> listings);

    ListingDetailsDto toDto(Listing listing);

    Listing toEntity(ListingDetailsDto listingDetailsDto);

}
