package ru.spbu.distolymp.repository.lists;

import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.lists.Division;

import java.util.Optional;

/**
 * @author Daria Usova
 */
public interface DivisionRepository extends CrudRepository<Division, Long> {

    Optional<Division> findFirstByOrderById();

}
