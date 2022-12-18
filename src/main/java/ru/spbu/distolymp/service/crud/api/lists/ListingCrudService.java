package ru.spbu.distolymp.service.crud.api.lists;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.entity.lists.Listing;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface ListingCrudService {
    Listing getListingByIdOrNull(Long id);
    List<ListingNameDto> getAllListings();

    void saveNewListing(ListingNameDto listingNameDto);

    void deleteListing(Long id);

    void renameListing(ListingNameDto listingNameDto);

    List<ListingNameDto> getListings(Specification<Listing> specs, Sort sort);
}
