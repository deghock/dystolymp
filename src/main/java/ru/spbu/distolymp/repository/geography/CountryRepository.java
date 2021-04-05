package ru.spbu.distolymp.repository.geography;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.entity.geography.Country;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface CountryRepository extends PagingAndSortingRepository<Country, Long>,
        JpaSpecificationExecutor<Country> {

    List<Country> findAllBy(Pageable pageable);

    List<Country> findAllBy(Sort sort);

    List<Country> findAll();

    void deleteAllByIdIn(List<Long> idList);

}
