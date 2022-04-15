package ru.spbu.distolymp.mapper.admin.tests.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spbu.distolymp.common.tasks.auxiliary.QuestionDto;
import ru.spbu.distolymp.common.tasks.auxiliary.QuestionType;
import ru.spbu.distolymp.common.tasks.parser.TestParser;
import ru.spbu.distolymp.dto.admin.tests.TestViewDto;
import ru.spbu.distolymp.entity.tasks.Test;
import ru.spbu.distolymp.mapper.admin.tests.api.TestViewMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Component
@RequiredArgsConstructor
public class TestViewMapperImpl implements TestViewMapper {
    @Override
    public TestViewDto toDto(Test test, String paramFileContent) {
        if (test == null) return null;
        TestViewDto testDto = new TestViewDto();
        List<QuestionDto> questions = TestParser.getQuestions(paramFileContent);

        int questionNumber = questions.size();
        if (TestParser.getRandomOrder(paramFileContent)) {
            int[] questionNumbers = TestParser.getQuestionsNumber(paramFileContent);
            Collections.shuffle(questions);
            List<QuestionDto> newQuestions = new ArrayList<>();
            questionNumber = 0;
            for (QuestionDto question : questions) {
                switch (question.getDifficulty()) {
                    case "Лёгкий":
                        if (questionNumbers[0] > 0) {
                            newQuestions.add(question);
                            questionNumber++;
                            questionNumbers[0] -= 1;
                        }
                        break;
                    case "Средний":
                        if (questionNumbers[1] > 0) {
                            newQuestions.add(question);
                            questionNumber++;
                            questionNumbers[1] -= 1;
                        }
                        break;
                    case "Сложный":
                        if (questionNumbers[2] > 0) {
                            newQuestions.add(question);
                            questionNumber++;
                            questionNumbers[2] -= 1;
                        }
                        break;
                }
            }
            questions = newQuestions;
        }
        for (QuestionDto question : questions) {
            if (question.getType() != QuestionType.L)
                question.setTrueAnswers(new String[] {"", "", "", "", ""});
            if (question.getType() == QuestionType.I) {
                String[] answers = question.getAnswers();
                answers[0] = "";
                question.setAnswers(answers);
            }
            if (question.getType() == QuestionType.F) {
                String[] answers = question.getAnswers();
                answers[0] = "";
                answers[1] = "";
                answers[2] = "";
                question.setAnswers(answers);
            }
            if (question.getType() != QuestionType.I && question.getType() != QuestionType.F) {
                List<String> answerList = new ArrayList<>();
                for (String answer : question.getAnswers())
                    if (!"".equals(answer)) answerList.add(answer);
                Collections.shuffle(answerList);
                while (answerList.size() < 5) answerList.add("");
                question.setAnswers(answerList.toArray(new String[5]));
            }
        }

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");

        testDto.setId(test.getId());
        testDto.setCurrentServerDateTime(dateTime.format(formatter));
        testDto.setTitle(test.getTitle());
        testDto.setQuestionNumber(questionNumber);
        testDto.setTestFolder(test.getTestFolder());
        testDto.setQuestionList(questions);
        testDto.setQuestionSkip(TestParser.getQuestionSkip(paramFileContent));

        return testDto;
    }
}
