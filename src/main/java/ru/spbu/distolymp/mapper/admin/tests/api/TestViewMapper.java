package ru.spbu.distolymp.mapper.admin.tests.api;

import ru.spbu.distolymp.dto.admin.tests.TestViewDto;
import ru.spbu.distolymp.entity.tasks.Test;

/**
 * @author Vladislav Konovalov
 */
public interface TestViewMapper {
    TestViewDto toDto(Test test, String paramFileContent);
}
