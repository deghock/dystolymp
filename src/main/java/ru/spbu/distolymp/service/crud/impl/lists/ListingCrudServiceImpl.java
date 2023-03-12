package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.entity.division.Division;
import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.exception.crud.lists.ListingCrudServiceException;
import ru.spbu.distolymp.mapper.entity.lists.listing.ListingNameMapper;
import ru.spbu.distolymp.repository.lists.ListingRepository;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.groups.ConstraintCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@Primary
@RequiredArgsConstructor
public class ListingCrudServiceImpl implements ListingCrudService {
    private final ListingNameMapper listingNameMapper;
    protected final ListingRepository listingRepository;
    private final DivisionCrudService divisionCrudService;
    private final ListingProblemCrudService listingProblemCrudService;
    private final ConstraintCrudService constraintCrudService;

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


    @Override
    @Transactional
    public void saveNewListing(ListingNameDto listingNameDto) {
        try{
            Listing listing = listingNameMapper.toEntity(listingNameDto);
            Division division = divisionCrudService.getAnyDivision();
            listing.setDivision(division);
            listingRepository.save(listing);
        } catch (DataAccessException e){
            log.error("An error occurred while adding a new list", e);
        }
    }

    @Override
    @Transactional
    public void deleteListing(Long id) {
        try{
            Listing listing = getListingByIdOrNull(id);
            listingRepository.delete(listing);
        }catch (Exception e){
            log.error("An error occurred while deleting a list", e);
        }
    }

    @Override
    @Transactional
    public void renameListing(ListingNameDto listingNameDto) {
        try{
            Long id = listingNameDto.getId();
            Listing listing = getListingByIdOrNull(id);
            listing.setName(listingNameDto.getName());
            listingRepository.save(listing);
        }catch (Exception e){
            log.error("An error occurred while renaming a list", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListingNameDto> getListings(Specification<Listing> specs, Sort sort) {
        try {
            List<Listing> listings = listingRepository.findAll(specs, sort);
            return listingNameMapper.toDtoList(listings);
        } catch (DataAccessException e) {
            log.error("An error occurred while renaming get listings", e);
            throw e;
        }
    }
}
