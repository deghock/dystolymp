package ru.spbu.distolymp.service.crud.api.lists;

import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.entity.lists.Listing;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface ListingCrudService {
    Listing getListingByIdOrNull(Long id);
    List<ListingNameDto> getAllListings();
}
