package ru.spbu.distolymp.service.crud.api.lists;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
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

    void renameListing(ListingNameDto listingNameDto);

    List<ListingNameDto> getListings(Specification<Listing> specs, Sort sort);

    void addAllFromList(Long copyId, Long id);

    void copyList(Long copyId, String listingName, String prefix);

    void updateOrder(Long id, Long problemId, Integer direction);

    void removeProblem(Long id, Long problemListingId);

    void removeConstraint(Long id);

    void setConstraint(Long id, ConstraintDto constraintDto);

    void addProblems(List<Long> problemDtoList, Long id);

    List<ProblemDto> getProblems();
}
