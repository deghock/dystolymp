package ru.spbu.distolymp.service.admin.tests.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.common.tasks.auxiliary.QuestionDto;
import ru.spbu.distolymp.dto.admin.tests.TestFilter;
import ru.spbu.distolymp.dto.admin.tests.TestListDto;
import ru.spbu.distolymp.dto.entity.tasks.TestDto;

/**
 * @author Vladislav Konovalov
 */
public interface TestService {
    void fillShowAllTestModelMap(ModelMap modelMap);
    void fillShowAllTestModelMap(ModelMap modelMap, TestFilter testFilter);
    void fillShowEditPageModelMap(Long id, ModelMap modelMap);
    void fillShowAddPageModelMap(ModelMap modelMap);
    void updateTest(TestDto testDto);
    void deleteTestWithFiles(Long id);
    void copyTest(TestListDto testTitleDto);
    void unarchiveTest(Long id);
    void deleteQuestionByNumber(Long testId, int number);
    void fillShowAddQuestionPageModelMap(Long testId, ModelMap modelMap);
    void updateQuestion(QuestionDto questionDto);
    void fillUpdateQuestionFailedModelMap(Long testId, ModelMap modelMap);
    void fillShowEditQuestionPageModelMap(Long testId, int number, ModelMap modelMap);
}
