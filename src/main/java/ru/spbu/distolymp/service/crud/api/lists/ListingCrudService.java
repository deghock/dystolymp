package ru.spbu.distolymp.service.crud.api.lists;

import ru.spbu.distolymp.dto.admin.directories.listing.ListingNameDto;
import ru.spbu.distolymp.entity.lists.Listing;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface ListingCrudService {

    Listing getListingById(Long id);

    List<ListingNameDto> getAllListingByDivisionId(Long divisionId);

}
