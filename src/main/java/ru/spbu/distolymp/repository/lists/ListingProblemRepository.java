package ru.spbu.distolymp.repository.lists;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.lists.ListingProblems;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface ListingProblemRepository extends CrudRepository<ListingProblems, Long> {
    List<ListingProblems> findAllByListingIdOrderByOrder(Long listingId);
    ListingProblems findFirstById(Long id);
    @Query("select distinct lp.listing.id from ListingProblems lp")
    List<Long> findDistinctListingIds();
    void deleteAllByProblemId(Long problemId);
}
