package ru.spbu.distolymp.service.admin.directories.lists.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;
import ru.spbu.distolymp.dto.admin.directories.lists.ListingFilter;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingProblemDto;
import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;

import java.util.List;

public interface ListingService {

    void fillShowAllListingsModelMap(ModelMap modelMap);

    void saveNewListing(ListingNameDto listingNameDto);

    void deleteListing(Long id);

    void renameListing(ListingNameDto listingNameDto);

    List<ListingNameDto> getListingsBy(ListingFilter listingFilter);

    //Frontend-часть не реализована

    List<ProblemDto> getAvailableProblems();

    List<ListingProblemDto> addProblems(List<Long> problemIds, Long id);

    void copyList(Long copyId, String newListingName, String prefix);

    void removeConstraint(Long id);

    ConstraintDto setConstraint(Long id, ConstraintDto constraintDto);

    List<ListingProblemDto> updateOrder(Long problemId, Long id, Integer direction);

    List<ListingProblemDto> removeProblem(Long problemId, Long id);

    List<ListingProblemDto> addAllFromList(Long copyId, Long id);
}
