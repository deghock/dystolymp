package ru.spbu.distolymp.mapper.entity.tasks.api;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;
import ru.spbu.distolymp.entity.tasks.Problem;

@Mapper
public interface ProblemMapper {
    Problem toEntity(ProblemDto problemDto);
    ProblemDto toDto(Problem problem);
}
