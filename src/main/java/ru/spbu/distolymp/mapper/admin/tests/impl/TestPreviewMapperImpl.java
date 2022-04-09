package ru.spbu.distolymp.mapper.admin.tests.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spbu.distolymp.dto.admin.tests.TestPreviewDto;
import ru.spbu.distolymp.entity.tasks.Test;
import ru.spbu.distolymp.mapper.admin.tests.api.TestPreviewMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Vladislav Konovalov
 */
@Component
@RequiredArgsConstructor
public class TestPreviewMapperImpl implements TestPreviewMapper {
    @Override
    public TestPreviewDto toDto(Test test) {
        if (test == null) return null;
        TestPreviewDto testDto = new TestPreviewDto();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");

        testDto.setId(test.getId());
        testDto.setPreviewText(test.getProblemText());
        testDto.setTestFolder(test.getTestFolder());
        testDto.setImageName(test.getImageFileName());
        testDto.setWidth(test.getWidth());
        testDto.setHeight(test.getHeight());
        testDto.setCurrentServerDateTime(dateTime.format(formatter));

        return testDto;
    }
}
