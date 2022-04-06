package ru.spbu.distolymp.controller.admin.tests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spbu.distolymp.common.tasks.auxiliary.QuestionDto;
import ru.spbu.distolymp.dto.admin.tests.TestFilter;
import ru.spbu.distolymp.dto.admin.tests.TestListDto;
import ru.spbu.distolymp.dto.entity.tasks.TestDto;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.service.admin.tests.api.TestService;
import javax.validation.Valid;

/**
 * @author Vladislav Konovalov
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/tests")
public class TestController {
    private static final String ROOT_DIR = "admin/tests/";
    private static final String LIST_PAGE = ROOT_DIR + "list";
    private static final String REDIRECT_LIST = "redirect:/tests/list";
    private static final String TESTS_TABLE = ROOT_DIR + "tests-table :: #tests-table";
    private static final String REDIRECT_EDIT = "redirect:/tests/edit/";
    private static final String EDIT_PAGE = ROOT_DIR + "edit";
    private static final String EDIT_QUESTION_PAGE = ROOT_DIR + "question-edit";
    private static final String PREVIEW_PAGE = ROOT_DIR + "preview";
    private static final String SUCCESS_PARAM = "success";
    private final TestService testService;

    @GetMapping("/list")
    public String getTests(ModelMap modelMap) {
        testService.fillShowAllTestModelMap(modelMap);
        return LIST_PAGE;
    }

    @GetMapping("/filter")
    public String getTestsByFilter(TestFilter testFilter, ModelMap modelMap) {
        testService.fillShowAllTestModelMap(modelMap, testFilter);
        return TESTS_TABLE;
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") Long id, ModelMap modelMap) {
        testService.fillShowEditPageModelMap(id, modelMap);
        return EDIT_PAGE;
    }

    @GetMapping("/add")
    public String getAddPage(ModelMap modelMap) {
        testService.fillShowAddPageModelMap(modelMap);
        return EDIT_PAGE;
    }

    @PostMapping("/save-or-edit")
    public String saveOrEdit(@Valid @ModelAttribute("test") TestDto testDto,
                             BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) return EDIT_PAGE;
        testService.updateTest(testDto);
        ra.addFlashAttribute(SUCCESS_PARAM, "Изменения сохранены");
        return REDIRECT_LIST;
    }

    @GetMapping("/delete/{id}")
    public String deleteTest(@PathVariable("id") Long id, RedirectAttributes ra) {
        testService.deleteTestWithFiles(id);
        ra.addFlashAttribute(SUCCESS_PARAM, "Тест удалён");
        return REDIRECT_LIST;
    }

    @PostMapping("/copy")
    public String copyTest(@ModelAttribute("testForCopy") TestListDto testDto,
                           RedirectAttributes ra) {
        testService.copyTest(testDto);
        ra.addFlashAttribute(SUCCESS_PARAM, "Тест скопирован");
        return REDIRECT_LIST;
    }

    @GetMapping("/unarchive/{id}")
    public String unarchiveTest(@PathVariable("id") Long id, RedirectAttributes ra) {
        testService.unarchiveTest(id);
        ra.addFlashAttribute(SUCCESS_PARAM, "Тест разархивирован");
        return REDIRECT_LIST;
    }

    @GetMapping("/edit/{id}/deleteQuestion/{number}")
    public String deleteQuestion(@PathVariable("id") Long testId, @PathVariable("number") int number,
                                 RedirectAttributes ra) {
        testService.deleteQuestionByNumber(testId, number);
        ra.addFlashAttribute(SUCCESS_PARAM, "Вопрос удалён");
        return REDIRECT_EDIT + testId;
    }

    @GetMapping("/edit/{testId}/add-question")
    public String getAddQuestionPage(@PathVariable("testId") Long testId, ModelMap modelMap) {
        testService.fillShowAddQuestionPageModelMap(testId, modelMap);
        return EDIT_QUESTION_PAGE;
    }

    @GetMapping("/edit/{testId}/edit-question/{number}")
    public String getEditQuestionPage(@PathVariable("testId") Long testId,
                                      @PathVariable("number") int number, ModelMap modelMap) {
        testService.fillShowEditQuestionPageModelMap(testId, number, modelMap);
        return EDIT_QUESTION_PAGE;
    }

    @PostMapping("/edit/save-or-edit-question")
    public String saveOrUpdateQuestion(@Valid @ModelAttribute("question") QuestionDto questionDto,
                                       BindingResult br, RedirectAttributes ra, ModelMap modelMap) {
        if (br.hasErrors()) {
            testService.fillUpdateQuestionFailedModelMap(questionDto.getTestId(), modelMap);
            return EDIT_QUESTION_PAGE;
        }
        testService.updateQuestion(questionDto);
        ra.addFlashAttribute(SUCCESS_PARAM, "Изменения сохранены");
        return REDIRECT_EDIT + questionDto.getTestId();
    }

    @GetMapping("/preview/{id}")
    public String previewTest(@PathVariable("id") Long id, ModelMap modelMap) {
        testService.fillShowPreviewPageModelMap(id, modelMap);
        return PREVIEW_PAGE;
    }

    @ExceptionHandler(TechnicalException.class)
    public String handleTechnicalException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Произошла техническая ошибка");
        return REDIRECT_LIST;
    }
}
