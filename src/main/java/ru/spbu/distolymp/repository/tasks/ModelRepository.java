package ru.spbu.distolymp.repository.tasks;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.entity.tasks.Model;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface ModelRepository extends PagingAndSortingRepository<Model, Long>, JpaSpecificationExecutor<Model> {
    List<Model> findByType(Integer type, Sort sort);
    Model findFirstById(Long id);
}
