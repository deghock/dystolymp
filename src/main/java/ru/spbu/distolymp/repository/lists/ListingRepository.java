package ru.spbu.distolymp.repository.lists;

import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.lists.Listing;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface ListingRepository extends CrudRepository<Listing, Long> {

    List<Listing> findAllByDivisionId(Long divisionId);

}
