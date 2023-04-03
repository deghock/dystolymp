package ru.spbu.distolymp.service.crud.impl.education;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.entity.education.SchoolType;
import ru.spbu.distolymp.repository.education.SchoolTypeRepository;
import ru.spbu.distolymp.service.crud.api.education.SchoolTypeCrudService;

import javax.persistence.EntityNotFoundException;

/**
 * @author Maxim Andreev
 */
@Log4j
@Service
@RequiredArgsConstructor
public class SchoolTypeCrudServiceImpl implements SchoolTypeCrudService {

    private final SchoolTypeRepository schoolTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public SchoolType getSchoolTypeById(Long id) {
        if (id == null || id == 0) return null;
        return schoolTypeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("School type with id=" + id + " was not found"));
    }
}
