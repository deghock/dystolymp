package ru.spbu.distolymp.repository.education;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.entity.education.School;

import java.util.List;

/**
 * @author Maxim Andreev
 */
public interface SchoolRepository extends PagingAndSortingRepository<School, Long>,
        JpaSpecificationExecutor<School> {
    List<School> findAll();
    List<School> findAllBy(Pageable pageable);

    List<School> findAllBy(Sort sort);
    void deleteSchoolsByIdIn(List<Long> idList);
}
