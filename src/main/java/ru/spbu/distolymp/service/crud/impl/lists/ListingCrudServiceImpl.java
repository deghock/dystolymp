package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.exception.crud.lists.ListingCrudServiceException;
import ru.spbu.distolymp.mapper.entity.lists.listing.ListingNameMapper;
import ru.spbu.distolymp.repository.lists.ListingRepository;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@RequiredArgsConstructor
public class ListingCrudServiceImpl implements ListingCrudService {
    private final ListingNameMapper listingNameMapper;
    protected final ListingRepository listingRepository;

    @Override
    @Transactional(readOnly = true)
    public Listing getListingByIdOrNull(Long id) {
        if (id == null)
            return null;
        try {
            return listingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while getting listing with id=" + id, e);
            throw new ListingCrudServiceException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListingNameDto> getAllListings() {
        try {
            List<Listing> listingList = (List<Listing>) listingRepository.findAll();
            return listingNameMapper.toDtoList(listingList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all listings", e);
            throw new ListingCrudServiceException();
        }
    }
}
