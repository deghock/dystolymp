package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.repository.lists.ListingRepository;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;

import javax.persistence.EntityNotFoundException;

/**
 * @author Vladislav Konovalov
 */
@Service
@RequiredArgsConstructor
public class ListingCrudServiceImpl implements ListingCrudService {

    protected final ListingRepository listingRepository;

    @Override
    @Transactional(readOnly = true)
    public Listing getListingById(Long id) {
        return listingRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Listing with id=" + id + " was not found"));
    }

}
