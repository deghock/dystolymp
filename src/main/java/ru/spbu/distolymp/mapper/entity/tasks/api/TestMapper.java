package ru.spbu.distolymp.mapper.entity.tasks.api;

import ru.spbu.distolymp.dto.entity.tasks.TestDto;
import ru.spbu.distolymp.entity.tasks.Test;

/**
 * @author Vladislav Konovalov
 */
public interface TestMapper {
    TestDto toDto(Test test);
    TestDto toDto(Test test, String paramFileContent);
    Test toEntity(TestDto testDto);
}
