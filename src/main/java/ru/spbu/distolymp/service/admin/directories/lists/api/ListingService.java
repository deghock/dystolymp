package ru.spbu.distolymp.service.admin.directories.lists.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.lists.ListingFilter;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;

import java.util.List;

public interface ListingService {

    void fillShowAllListingsModelMap(ModelMap modelMap);

    void saveNewListing(ListingNameDto listingNameDto);

    void deleteListing(Long id);

    void renameListing(ListingNameDto listingNameDto);

    List<ListingNameDto> getListingsBy(ListingFilter listingFilter);
}
