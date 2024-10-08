package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.extern.log4j.Log4j;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingDetailsDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingProblemDto;
import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;
import ru.spbu.distolymp.entity.division.Division;
import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.entity.lists.ListingProblems;
import ru.spbu.distolymp.exception.crud.lists.ListingCrudServiceException;
import ru.spbu.distolymp.mapper.admin.directories.groups.ConstraintMapper;
import ru.spbu.distolymp.mapper.entity.lists.ListingDetailsMapper;
import ru.spbu.distolymp.mapper.entity.lists.ListingNameMapper;
import ru.spbu.distolymp.repository.lists.ListingRepository;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.groups.GroupsCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import ru.spbu.distolymp.service.crud.api.tasks.ProblemCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@Primary
public class ListingCrudServiceImpl implements ListingCrudService {
    private final ListingNameMapper listingNameMapper;
    protected final ListingRepository listingRepository;
    private final DivisionCrudService divisionCrudService;
    private final ListingProblemCrudService listingProblemCrudService;
    private final ConstraintMapper constraintMapper;
    private final ProblemCrudService problemCrudService;
    private final GroupsCrudService groupsCrudService;
    private final ListingDetailsMapper listingDetailsMapper;

    @Autowired
    public ListingCrudServiceImpl(ListingNameMapper listingNameMapper, ListingRepository listingRepository, DivisionCrudService divisionCrudService, ListingProblemCrudService listingProblemCrudService, ConstraintMapper constraintMapper, ProblemCrudService problemCrudService,@Lazy GroupsCrudService groupsCrudService, ListingDetailsMapper listingDetailsMapper) {
        this.listingNameMapper = listingNameMapper;
        this.listingRepository = listingRepository;
        this.divisionCrudService = divisionCrudService;
        this.listingProblemCrudService = listingProblemCrudService;
        this.constraintMapper = constraintMapper;
        this.problemCrudService = problemCrudService;
        this.groupsCrudService = groupsCrudService;
        this.listingDetailsMapper = listingDetailsMapper;
    }

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
            groupsCrudService.removeListingFromAllGroupsByListingId(id);
            listingRepository.delete(listing);
        }catch (Exception e){
            log.error("An error occurred while deleting a list", e);
        }
    }

    @Override
    @Transactional
    public ListingNameDto renameListing(ListingNameDto listingDetailsDto) {
        try{
            Long id = listingDetailsDto.getId();
            Listing listing = getListingByIdOrNull(id);
            listing.setName(listingDetailsDto.getName());
            listingRepository.save(listing);
            return listingNameMapper.toDto(listing);
        }catch (Exception e){
            log.error("An error occurred while renaming a list", e);
            throw e;
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

    @Override
    @Transactional(readOnly = true)
    public List<ProblemDto> getAvailableProblems(){
        return problemCrudService.getAvailableProblems();
    }

    @Override
    @Transactional(readOnly = true)
    public ListingDetailsDto getListingById(Long id) {
        try{
            Listing listing = listingRepository.findFirstById(id);
            listing.getProblemList().sort(ListingProblems::compareTo);
            return listingDetailsMapper.toDto(listing);

        }catch (DataException e){
            log.error("An error occurred while loading a single listing", e);
            throw e;
        }
    }


    //TODO:Возможно этот метод стоит перенести в ListingProblemCrudService и возможно кто-то решит это переписать перед релизом
    @Override
    @Transactional
    public List<ListingProblemDto> addProblems(List<Long> problemDtoList, Long id){
        try{
            Listing listing = getListingByIdOrNull(id);
            List<ListingProblems> problems = listing.getProblemList();
            int problemsSize = problems.size();
            int decrement = 0;
            for(int i = 0; i < problemDtoList.size(); i++){
                int finalI = i;
                if(problems.stream().noneMatch(o -> problemDtoList.get(finalI).equals(o.getProblem().getId()))){
                    problems.add(new ListingProblems());
                    problems.get(i + problemsSize - decrement).setListing(getListingByIdOrNull(id));
                    problems.get(i + problemsSize - decrement).setProblem(problemCrudService.getProblemById(problemDtoList.get(i)));
                    problems.get(i + problemsSize - decrement).setOrder(i + problemsSize + 1 - decrement);
                }else{
                    decrement++;
                }
            }
            problems.sort(ListingProblems::compareTo);
            listing.setProblemList(problems);
            listingRepository.save(listing);
            return listingDetailsMapper.toDto(listing).getProblemList();
        }catch (DataAccessException e){
            log.error("An error occurred while adding problems to list", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public ConstraintDto setConstraint(Long id, ConstraintDto constraintDto){
        try{
            Listing listing = getListingByIdOrNull(id);
            listing.setConstraint(constraintMapper.toEntity(constraintDto));
            listingRepository.save(listing);
            return listingDetailsMapper.toDto(listing).getConstraint();
        }catch (Exception e){
            log.error("An error occurred while setting constraint to list", e);
            throw e;
        }
    }

    @Override
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

    //TODO:Возможно этот метод стоит перенести в ListingProblemCrudService
    @Override
    @Transactional
    public List<ListingProblemDto> removeProblem(Long id, Long problemListingId){
        try{
            Listing listing = getListingByIdOrNull(id);
            ListingProblems listingProblems = listingProblemCrudService.findByIdOrNull(problemListingId);
            listing.getProblemList().remove(listingProblems);
            listing.getProblemList().sort(ListingProblems::compareTo);
            for (int i = 0; i < listing.getProblemList().size(); i++)
                listing.getProblemList().get(i).setOrder(i + 1);
            listingRepository.save(listing);
            return listingDetailsMapper.toDto(listing).getProblemList();
        }catch (Exception e){
            log.error("An error occurred while removing problem from list", e);
            throw e;
        }
    }

    //TODO:Возможно этот метод стоит перенести в ListingProblemCrudService. Вот этот врядли. Удобнее работать с ордером отсюда как мне кажется
    @Override
    @Transactional
    public List<ListingProblemDto> updateOrder(Long id, Long problemId, Integer direction){
        try{
            Listing listing = getListingByIdOrNull(id);
            if(listing != null){
                listing.setProblemList(updateProblemOrder(listingProblemCrudService.findByIdOrNull(problemId), listing.getProblemList(), direction));
                listingRepository.save(listing);
                return listingDetailsMapper.toDto(listing).getProblemList();
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
        System.out.println(index);
        System.out.println(listingProblemsList.get(index).getOrder());
        System.out.println(direction);
        if(direction == 1){
            if(index != 0){
                listingProblemsList.get(index).setOrder(listingProblemsList.get(index).getOrder() - 1);
                listingProblemsList.get(index - 1).setOrder(listingProblemsList.get(index - 1).getOrder() + 1);
            }
        } else if(index != listingProblemsList.size() - 1){
            listingProblemsList.get(index).setOrder(listingProblemsList.get(index).getOrder() + 1);
            listingProblemsList.get(index + 1).setOrder(listingProblemsList.get(index + 1).getOrder() - 1);
        }
        listingProblemsList.sort(ListingProblems::compareTo);
        return listingProblemsList;
    }

    @Override
    @Transactional
    public void copyList(Long copyId, String listingName, String prefix){
        Listing copyListing = getListingByIdOrNull(copyId);
        Listing newListing = new Listing();
        newListing.setName(listingName);
        newListing.setProblemList(listingProblemCrudService.copyListingProblem(copyListing.getProblemList(), newListing, prefix));
        newListing.setDivision(divisionCrudService.getAnyDivision());
        listingRepository.save(newListing);
    }

    @Override
    @Transactional
    public List<ListingProblemDto> addAllFromList(Long copyId, Long id){
        List<ListingProblems> copyListing = getListingByIdOrNull(copyId).getProblemList();
        List<Long> problemIds = new ArrayList<>();
        for (ListingProblems problems : copyListing) {
            problemIds.add(problems.getProblem().getId());
        }
        return addProblems(problemIds, id);
    }
}
