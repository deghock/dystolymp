package ru.spbu.distolymp.service.crud.impl.groups;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;
import ru.spbu.distolymp.entity.groups.Constraint;
import ru.spbu.distolymp.mapper.admin.directories.groups.ConstraintMapper;
import ru.spbu.distolymp.repository.groups.ConstraintRepository;
import ru.spbu.distolymp.service.crud.api.groups.ConstraintCrudService;

@Log4j
@Service
@RequiredArgsConstructor
public class ConstraintCrudServiceImpl implements ConstraintCrudService {
    private final ConstraintRepository constraintRepository;
    private final ConstraintMapper constraintMapper;

    @Override
    @Transactional
    public void setConstraint(ConstraintDto constraintDto) {
        Constraint constraint = constraintMapper.toEntity(constraintDto);
        constraintRepository.save(constraint);
    }

    @Override
    public void removeConstraint(Long id) {
        constraintRepository.deleteById(id);
    }
}
