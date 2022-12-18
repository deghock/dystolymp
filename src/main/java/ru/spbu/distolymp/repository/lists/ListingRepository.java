package ru.spbu.distolymp.repository.lists;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.entity.lists.Listing;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface ListingRepository extends PagingAndSortingRepository<Listing, Long>,
        JpaSpecificationExecutor<Listing> {

    List<Listing> findAllByOrderById();
}