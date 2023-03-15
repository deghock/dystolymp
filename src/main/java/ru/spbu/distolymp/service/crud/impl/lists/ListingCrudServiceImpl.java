package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.exception.DataException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;
import ru.spbu.distolymp.entity.division.Division;
import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.entity.lists.ListingProblems;
import ru.spbu.distolymp.exception.crud.lists.ListingCrudServiceException;
import ru.spbu.distolymp.mapper.admin.directories.groups.ConstraintMapper;
import ru.spbu.distolymp.mapper.entity.lists.listing.ListingNameMapper;
import ru.spbu.distolymp.repository.lists.ListingRepository;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import ru.spbu.distolymp.service.crud.api.tasks.ProblemCrudService;

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
    private final ConstraintMapper constraintMapper;
    private final ProblemCrudService problemCrudService;

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
        } catch (DataException e){
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

    @Transactional(readOnly = true)
    protected List<ProblemDto> getProblems(){
        return problemCrudService.getAvailableProblems();
    }

    @Transactional
    public void addProblems(List<ProblemDto> problemDtoList, Long id){
        try{
            Listing listing = getListingByIdOrNull(id);
            List<ListingProblems> problems = listing.getProblemList();
            for(int i = 0; i < problemDtoList.size(); i++){
                problems.add(new ListingProblems());
                problems.get(i + problems.size()).setListing(getListingByIdOrNull(id));
                problems.get(i + problems.size()).setProblem(problemCrudService.getProblemById(problemDtoList.get(i).getId()));
                problems.get(i + problems.size()).setOrder(i + problems.size() + 1);
            }
            listing.setProblemList(problems);
            listingRepository.save(listing);
        }catch (DataAccessException e){
            log.error("An error occurred while adding problems to list", e);
            throw e;
        }
    }

    @Transactional
    public void setConstraint(Long id, ConstraintDto constraintDto) {
        try{
            Listing listing = getListingByIdOrNull(id);
            listing.setConstraint(constraintMapper.toEntity(constraintDto));
            listingRepository.save(listing);
        }catch (Exception e){
            log.error("An error occurred while setting constraint to list", e);
            throw e;
        }
    }

    @Transactional
    public void removeConstraint(Long id){
        try{
            Listing listing = getListingByIdOrNull(id);
            listing.setConstraint(null);
            listingRepository.save(listing);
        }catch (Exception e){
            log.error("An error occurred while removing constraint from list", e);
            throw e;
        }
    }

    @Transactional
    public void removeProblem(Long id, Long problemListingId){
        try{
            Listing listing = getListingByIdOrNull(id);
            ListingProblems listingProblems = listingProblemCrudService.findByIdOrNull(problemListingId);
            listing.getProblemList().remove(listingProblems);
            listingRepository.save(listing);
        }catch (Exception e){
            log.error("An error occurred while removing problem from list", e);
            throw e;
        }
    }

    @Transactional
    public void updateOrder(Long id, Long problemId, Integer direction){
        try{
            Listing listing = getListingByIdOrNull(id);
            if(listing != null){
                listing.setProblemList(updateProblemOrder(listingProblemCrudService.findByIdOrNull(problemId), listing.getProblemList(), direction));
                listingRepository.save(listing);
            }else{
                throw new EntityNotFoundException();
            }
        }catch (Exception e){
            log.error("An error occurred while updating order in list", e);
            throw e;
        }
    }

    private List<ListingProblems> updateProblemOrder(ListingProblems listingProblems, List<ListingProblems> listingProblemsList, Integer direction){
        listingProblemsList.sort(ListingProblems::compareTo);
        int index = listingProblemsList.indexOf(listingProblems);
        if(index != -1)
        {
            if(direction == 1){
                if(index != 0){
                    listingProblemsList.get(index).setOrder(listingProblemsList.get(index).getOrder() - 1);
                    listingProblemsList.get(index - 1).setOrder(listingProblemsList.get(index).getOrder() + 1);
                }
            }
            else if(index != listingProblemsList.size() - 1){
                listingProblemsList.get(index).setOrder(listingProblemsList.get(index).getOrder() + 1);
                listingProblemsList.get(index - 1).setOrder(listingProblemsList.get(index).getOrder() - 1);
            }
        }
        return listingProblemsList;
    }
}
