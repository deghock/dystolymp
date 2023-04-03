package ru.spbu.distolymp.mapper.admin.directories.groups;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;
import ru.spbu.distolymp.entity.groups.Constraint;

@Mapper
public interface ConstraintMapper {
    Constraint toEntity(ConstraintDto constraintDto);
    ConstraintDto toDto(Constraint constraint);
}
