package ru.spbu.distolymp.service.crud.api.groups;

import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;

public interface ConstraintCrudService {
   void removeConstraint(Long id);
   void setConstraint(ConstraintDto constraintDto);
}
