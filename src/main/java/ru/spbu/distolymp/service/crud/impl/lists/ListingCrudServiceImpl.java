package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.geography.country.CountryDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.entity.division.Category;
import ru.spbu.distolymp.entity.division.Division;
import ru.spbu.distolymp.entity.geography.Country;
import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.exception.crud.division.category.DeleteCategoryException;
import ru.spbu.distolymp.exception.crud.division.category.UpdateCategoryNameException;
import ru.spbu.distolymp.exception.crud.education.grade.AddNewGradeException;
import ru.spbu.distolymp.exception.crud.geography.CountryCrudServiceException;
import ru.spbu.distolymp.exception.crud.lists.ListingCrudServiceException;
import ru.spbu.distolymp.mapper.entity.lists.listing.ListingNameMapper;
import ru.spbu.distolymp.repository.lists.ListingRepository;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;
import javax.persistence.EntityNotFoundException;
import java.util.List;


/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@RequiredArgsConstructor
@Primary
public class ListingCrudServiceImpl implements ListingCrudService {

    private final ListingNameMapper listingNameMapper;
    protected final ListingRepository listingRepository;
    private final DivisionCrudService divisionCrudService;

    @Override
    @Transactional(readOnly = true)
    public List<ListingNameDto> getAllListings() {
        try {
            List<Listing> listingList = listingRepository.findAllByOrderById();
            return listingNameMapper.toDtoList(listingList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all listings", e);
            throw new ListingCrudServiceException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Listing getListingById(Long id) {
        try {
            return listingRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Listing with id=" + id + " was not found"));
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while deleting listing with id=" + id, e);
            throw e;
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
            log.error("An error occurred while adding a new grade", e);
            throw new AddNewGradeException();
        }
    }

    @Override
    @Transactional
    public void deleteListing(Long id) {
        Listing listing = getListingById(id);
        listingRepository.delete(listing);
    }

    @Override
    @Transactional
    public void renameListing(ListingNameDto listingNameDto) {
       Long id = listingNameDto.getId();
       Listing listing = getListingById(id);
       listing.setName(listingNameDto.getName());
       listingRepository.save(listing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListingNameDto> getListings(Specification<Listing> specs, Sort sort) {
        System.out.println("It works 2");
        try {
            System.out.println("It works 3");
            List<Listing> listings = listingRepository.findAll(specs, sort);
            return listingNameMapper.toDtoList(listings);
        } catch (DataAccessException e) {
            throw e;
        }
    }
}
