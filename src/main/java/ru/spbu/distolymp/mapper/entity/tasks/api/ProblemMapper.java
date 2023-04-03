package ru.spbu.distolymp.mapper.entity.tasks.api;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;
import ru.spbu.distolymp.entity.tasks.Problem;
import java.util.List;

@Mapper
public interface ProblemMapper {
    Problem toEntity(ProblemDto problemDto);
    ProblemDto toDto(Problem problem);
    List<ProblemDto> toDtoList(List<Problem> problemList);
}
