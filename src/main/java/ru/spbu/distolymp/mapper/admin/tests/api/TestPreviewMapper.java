package ru.spbu.distolymp.mapper.admin.tests.api;

import ru.spbu.distolymp.dto.admin.tests.TestPreviewDto;
import ru.spbu.distolymp.entity.tasks.Test;

/**
 * @author Vladislav Konovalov
 */
public interface TestPreviewMapper {
    TestPreviewDto toDto(Test test);
}
