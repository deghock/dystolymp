package ru.spbu.distolymp.service.crud.api.lists;

import ru.spbu.distolymp.entity.lists.Listing;

/**
 * @author Vladislav Konovalov
 */
public interface ListingCrudService {

    Listing getListingById(Long id);

}
