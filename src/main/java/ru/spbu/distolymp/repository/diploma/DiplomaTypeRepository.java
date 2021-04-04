package ru.spbu.distolymp.repository.diploma;

import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.diploma.DiplomaType;

import java.util.Optional;

/**
 * @author Daria Usova
 */
public interface DiplomaTypeRepository extends CrudRepository<DiplomaType, Integer> {

    Optional<DiplomaType> findDiplomaTypeByName(String name);

}