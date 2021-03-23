package ru.spbu.distolymp.mapper.admin.directories.lists.listing;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.admin.directories.lists.listing.ListingNameDto;
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
