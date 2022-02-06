package ru.spbu.distolymp.controller.admin.models;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spbu.distolymp.dto.admin.models.ModelFilter;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.service.admin.models.api.ModelService;

/**
 * @author Vladislav Konovalov
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/models")
public class ModelController {
    private static final String ROOT_DIR = "admin/models/";
    private static final String LIST_PAGE = ROOT_DIR + "list";
    private static final String REDIRECT_LIST = "redirect:/models/list";
    private static final String MODELS_TABLE = ROOT_DIR + "models-table :: #models-table";
    private static final String EDIT_PAGE = ROOT_DIR + "edit";
    private final ModelService modelService;

    @GetMapping("/list")
    public String getModels(ModelMap modelMap) {
        modelService.fillShowAllModelModelMap(modelMap);
        return LIST_PAGE;
    }

    @GetMapping("/filter")
    public String getModelsByFilter(ModelFilter modelFilter, ModelMap modelMap) {
        modelService.fillShowAllModelModelMap(modelMap, modelFilter);
        return MODELS_TABLE;
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") Long id, ModelMap modelMap) {
        modelService.fillShowEditPageModelMap(id, modelMap);
        return EDIT_PAGE;
    }

    @GetMapping("/add")
    public String getAddPage(ModelMap modelMap) {
        modelService.fillShowAddPageModelMap(modelMap);
        return EDIT_PAGE;
    }

    @ExceptionHandler(TechnicalException.class)
    public String handleTechnicalException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Произошла техническая ошибка");
        return REDIRECT_LIST;
    }
}
