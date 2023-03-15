package ru.spbu.distolymp.service.admin.directories.lists.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;
import ru.spbu.distolymp.dto.admin.directories.lists.ListingFilter;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;

import java.util.List;

public interface ListingService {

    void fillShowAllListingsModelMap(ModelMap modelMap);

    void saveNewListing(ListingNameDto listingNameDto);

    void deleteListing(Long id);

    void renameListing(ListingNameDto listingNameDto);

    List<ListingNameDto> getListingsBy(ListingFilter listingFilter);

    List<ProblemDto> getAvailableProblems();

    void addProblems(List<ProblemDto> problemDtoList, Long id);

    void copyList(Long copyId, Long id, String newListingName, String prefix);

    void removeConstraint(Long id);

    void setConstraint(Long id, ConstraintDto constraintDto);

    void updateOrder(Long problemId, Long id, Integer direction);

    void removeProblem(Long problemId, Long id);

    void addAllFromList(Long copyId, Long id);
}
