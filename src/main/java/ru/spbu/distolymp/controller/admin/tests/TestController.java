package ru.spbu.distolymp.controller.admin.tests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spbu.distolymp.dto.admin.tests.TestFilter;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.service.admin.tests.api.TestService;

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
    private static final String EDIT_PAGE = ROOT_DIR + "edit";
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

    @ExceptionHandler(TechnicalException.class)
    public String handleTechnicalException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Произошла техническая ошибка");
        return REDIRECT_LIST;
    }
}
