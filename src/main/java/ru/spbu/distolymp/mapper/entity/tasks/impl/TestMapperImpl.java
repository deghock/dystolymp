package ru.spbu.distolymp.mapper.entity.tasks.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spbu.distolymp.common.tasks.parser.TestParser;
import ru.spbu.distolymp.dto.entity.tasks.TestDto;
import ru.spbu.distolymp.entity.tasks.Test;
import ru.spbu.distolymp.mapper.entity.tasks.api.TestMapper;

/**
 * @author Vladislav Konovalov
 */
@Component
@RequiredArgsConstructor
public class TestMapperImpl implements TestMapper {
    @Override
    public TestDto toDto(Test test) {
        return toDto(test, null);
    }

    @Override
    public TestDto toDto(Test test, String paramFileContent) {
        if (test == null) return null;
        TestDto testDto = new TestDto();

        testDto.setId(test.getId());
        testDto.setStatus(test.getStatus());
        testDto.setPrefix(test.getPrefix());
        testDto.setTitle(test.getTitle());
        testDto.setProblemText(test.getProblemText());
        testDto.setImageFileName(test.getImageFileName());
        testDto.setWidth(test.getWidth());
        testDto.setHeight(test.getHeight());
        testDto.setShowResult(test.isShowResult());
        testDto.setMinusPoints(test.getMinusPoints());
        testDto.setTestFolder(test.getTestFolder());
        testDto.setBrcFileName(test.getBrcFileName());
        testDto.setParamFileName(test.getParFileName());
        testDto.setPoints(test.getPoints());

        if (paramFileContent != null) {
            testDto.setRandomOrder(TestParser.getRandomOrder(paramFileContent));
            testDto.setQuestionsNumber(TestParser.getQuestionsNumber(paramFileContent));
            testDto.setQuestionSkip(TestParser.getQuestionSkip(paramFileContent));
            testDto.setQuestionList(TestParser.getQuestions(paramFileContent));
            testDto.setAllQuestionsNumber(TestParser.getAllQuestionsNumber(testDto.getQuestionList()));
        }

        return testDto;
    }

    @Override
    public Test toEntity(TestDto testDto) {
        if (testDto == null) return null;
        Test test = new Test();

        test.setId(testDto.getId());
        test.setTitle(testDto.getTitle());
        test.setPrefix(testDto.getPrefix());
        test.setStatus(testDto.getStatus());
        test.setProblemText(testDto.getProblemText());
        test.setImageFileName(testDto.getImageFileName());
        test.setWidth(testDto.getWidth());
        test.setHeight(testDto.getHeight());
        test.setMinusPoints(testDto.getMinusPoints());
        test.setTestFolder(testDto.getTestFolder());
        test.setBrcFileName(testDto.getBrcFileName());
        test.setParFileName(testDto.getParamFileName());
        test.setShowResult(testDto.isShowResult());
        test.setPoints(testDto.getPoints());

        return test;
    }
}
