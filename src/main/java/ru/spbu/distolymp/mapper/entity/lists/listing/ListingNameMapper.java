package ru.spbu.distolymp.mapper.entity.lists.listing;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.entity.lists.Listing;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface ListingNameMapper {

    List<ListingNameDto> toDtoList(List<Listing> listings);

    ListingNameDto toDto(Listing listing);

    Listing toEntity(ListingNameDto listingNameDto);

}
