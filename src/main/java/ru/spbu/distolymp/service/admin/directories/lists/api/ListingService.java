package ru.spbu.distolymp.service.admin.directories.lists.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;
import ru.spbu.distolymp.dto.admin.directories.lists.ListingFilter;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;

import java.util.List;

public interface ListingService {

    void fillShowAllListingsModelMap(ModelMap modelMap);

    void saveNewListing(ListingNameDto listingNameDto);

    void deleteListing(Long id);

    void renameListing(ListingNameDto listingNameDto);

    void getListingsBy(ModelMap modelMap, ListingFilter listingFilter);

    void getAvailableProblems(ModelMap modelMap);

    void addProblems(List<Long> problemIds, Long id, ModelMap modelMap);

    void copyList(Long copyId, String newListingName, String prefix);

    void removeConstraint(Long id, ModelMap modelMap);

    void setConstraint(Long id, ConstraintDto constraintDto, ModelMap modelMap);

    void updateOrder(Long problemId, Long id, Integer direction, ModelMap modelMap);

    void removeProblem(Long problemId, Long id, ModelMap modelMap);

    void addAllFromList(Long copyId, Long id, ModelMap modelMap);
}
