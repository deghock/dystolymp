package ru.spbu.distolymp.service.crud.api.lists;

import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.entity.lists.ListingProblems;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface ListingProblemCrudService {
    void deleteByProblemId(Long problemId);
    List<ListingProblems> copyListingProblem(List<ListingProblems> copyListingProblem, Listing copyListing, String prefix);
    ListingProblems findByIdOrNull(Long id);
}
