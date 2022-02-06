package ru.spbu.distolymp.repository.tasks;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.entity.tasks.Test;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface TestRepository extends PagingAndSortingRepository<Test, Long>, JpaSpecificationExecutor<Test> {
    List<Test> findByType(Integer type, Sort sort);
}
