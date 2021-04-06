package ru.spbu.distolymp.repository.lists;

import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.lists.Listing;

/**
 * @author Vladislav Konovalov
 */
public interface ListingRepository extends CrudRepository<Listing, Long> {
}
