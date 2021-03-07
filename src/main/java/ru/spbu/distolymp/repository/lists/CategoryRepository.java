package ru.spbu.distolymp.repository.lists;

import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.lists.Category;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findByDivisionId(Long divisionId);

}
