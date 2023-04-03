package ru.spbu.distolymp.service.admin.directories.lists.impl;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;
import ru.spbu.distolymp.dto.admin.directories.lists.ListingFilter;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingDetailsDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.mapper.admin.directories.groups.ConstraintMapper;
import ru.spbu.distolymp.mapper.entity.lists.ListingDetailsMapper;
import ru.spbu.distolymp.mapper.entity.lists.ListingNameMapper;
import ru.spbu.distolymp.repository.lists.ListingRepository;
import ru.spbu.distolymp.service.admin.directories.lists.api.ListingService;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.groups.GroupsCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import ru.spbu.distolymp.service.crud.api.tasks.ProblemCrudService;
import ru.spbu.distolymp.service.crud.impl.lists.ListingCrudServiceImpl;
import ru.spbu.distolymp.util.admin.directories.lists.ListingsSpecConverter;

import java.util.List;


@Service
public class ListingServiceImpl extends ListingCrudServiceImpl implements ListingService {

    private static final Sort SORT_BY_NAME_ASC = Sort.by("name").ascending();

    public ListingServiceImpl(ListingRepository listingRepository, ListingNameMapper listingNameMapper, ListingDetailsMapper listingDetailsMapper, GroupsCrudService groupsCrudService,
                              DivisionCrudService divisionCrudService, ListingProblemCrudService listingProblemCrudService, ConstraintMapper constraintMapper, ProblemCrudService problemCrudService) {
        super(listingNameMapper, listingRepository, divisionCrudService, listingProblemCrudService, constraintMapper, problemCrudService, groupsCrudService, listingDetailsMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllListingsModelMap(ModelMap modelMap) {
        modelMap.put("listing", new ListingNameDto());
        modelMap.put("listings", getAllListings());
    }

    @Override
    public void renameListing(ModelMap modelMap, ListingNameDto listingNameDto) {

        modelMap.put("singleListing", renameListing(listingNameDto));
    }

    @Override
    @Transactional(readOnly = true)
    public void getListingsBy(ModelMap modelMap, ListingFilter listingFilter) {
        Specification<Listing> specs = ListingsSpecConverter.toSpecs(listingFilter);
        if(specs == null){
            getAllListings();
        }
        modelMap.put("listings", getListings(specs, SORT_BY_NAME_ASC));
    }

    @Override
    @Transactional(readOnly = true)
    public void getAvailableProblems(ModelMap modelMap) {
        modelMap.put("problems", getAvailableProblems());;
    }

    @Override
    @Transactional
    public void addProblems(List<Long> problemIds, Long id, ModelMap modelMap) {
        modelMap.put("problemList", addProblems(problemIds, id));
    }

    @Override
    @Transactional
    public void removeConstraint(Long id, ModelMap modelMap) {
        removeConstraint(id);
        modelMap.remove("constraint");
        modelMap.put("constraint", null);
    }

    @Override
    @Transactional
    public void setConstraint(Long id, ConstraintDto constraintDto, ModelMap modelMap) {
        modelMap.remove("constraint");
        modelMap.put("constraint", setConstraint(id, constraintDto));
    }

    @Override
    @Transactional
    public void updateOrder(Long problemId, Long id, Integer direction, ModelMap modelMap) {
        modelMap.remove("problemList");
        modelMap.put("problemList", updateOrder(id, problemId, direction));
    }

    @Override
    @Transactional
    public void removeProblem(Long problemId, Long id, ModelMap modelMap) {
        modelMap.remove("problemList");
        modelMap.put("problemList", removeProblem(id, problemId));
    }

    @Override
    @Transactional
    public void addAllFromList(Long copyId, Long id, ModelMap modelMap) {
        modelMap.remove("problemList");
        modelMap.put("problemList", addAllFromList(copyId, id));
    }

    @Override
    @Transactional(readOnly = true)
    public void getSingleListing(Long id, ModelMap modelMap) {
        modelMap.remove("singleListing");
        ListingDetailsDto listingDetailsDto = getListingById(id);
        modelMap.put("singleListing", listingDetailsDto);
        modelMap.put("problemList", listingDetailsDto.getProblemList());
    }
}
