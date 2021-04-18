package ru.spbu.distolymp.repository.division;

import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.division.Category;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findAll();

}
