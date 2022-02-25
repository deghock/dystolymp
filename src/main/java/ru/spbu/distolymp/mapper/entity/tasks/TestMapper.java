package ru.spbu.distolymp.mapper.entity.tasks;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.tasks.TestDto;
import ru.spbu.distolymp.entity.tasks.Test;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface TestMapper {
    TestDto toDto(Test test);
    Test toEntity(TestDto testDto);
}
