package ru.spbu.distolymp.service.admin.directories.lists.impl;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.lists.ListingFilter;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.mapper.admin.directories.groups.ConstraintMapper;
import ru.spbu.distolymp.mapper.entity.lists.ListingNameMapper;
import ru.spbu.distolymp.repository.lists.ListingRepository;
import ru.spbu.distolymp.service.admin.directories.lists.api.ListingService;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import ru.spbu.distolymp.service.crud.api.tasks.ProblemCrudService;
import ru.spbu.distolymp.service.crud.impl.lists.ListingCrudServiceImpl;
import ru.spbu.distolymp.util.admin.directories.lists.ListingsSpecConverter;

import java.util.List;


@Service
public class ListingServiceImpl extends ListingCrudServiceImpl implements ListingService {

    private static final Sort SORT_BY_NAME_ASC = Sort.by("name").ascending();

    public ListingServiceImpl(ListingRepository listingRepository, ListingNameMapper listingNameMapper,
                              DivisionCrudService divisionCrudService, ListingProblemCrudService listingProblemCrudService, ConstraintMapper constraintMapper, ProblemCrudService problemCrudService) {
        super(listingNameMapper, listingRepository, divisionCrudService, listingProblemCrudService, constraintMapper, problemCrudService);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllListingsModelMap(ModelMap modelMap) {
        modelMap.put("listing", new ListingNameDto());
        modelMap.put("listings", getAllListings());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListingNameDto> getListingsBy(ListingFilter listingFilter) {
        Specification<Listing> specs = ListingsSpecConverter.toSpecs(listingFilter);
        if(specs == null){
            getAllListings();
        }
        return getListings(specs, SORT_BY_NAME_ASC);
    }
}
