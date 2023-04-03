package ru.spbu.distolymp.service.crud.api.lists;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingDetailsDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingProblemDto;
import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;
import ru.spbu.distolymp.entity.lists.Listing;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface ListingCrudService {
    Listing getListingByIdOrNull(Long id);

    List<ListingNameDto> getAllListings();

    void saveNewListing(ListingNameDto listingNameDto);

    void deleteListing(Long id);

    ListingNameDto renameListing(ListingNameDto listingDetailsDto);

    List<ListingNameDto> getListings(Specification<Listing> specs, Sort sort);

    List<ListingProblemDto> addProblems(List<Long> problemIds, Long id);

    void copyList(Long copyId, String newListingName, String prefix);

    void removeConstraint(Long id);

    ConstraintDto setConstraint(Long id, ConstraintDto constraintDto);

    List<ListingProblemDto> updateOrder(Long problemId, Long id, Integer direction);

    List<ListingProblemDto> removeProblem(Long problemId, Long id);

    List<ListingProblemDto> addAllFromList(Long copyId, Long id);

    List<ProblemDto> getAvailableProblems();

    ListingDetailsDto getListingById(Long id);
}
