package ru.spbu.distolymp.controller.admin.directories.towns;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spbu.distolymp.dto.admin.directories.towns.TownDetailsDto;
import ru.spbu.distolymp.exception.crud.geography.TownCrudServiceException;
import ru.spbu.distolymp.service.admin.directories.towns.api.TownService;
import javax.validation.Valid;

/**
 * @author Vladislav Konovalov
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/towns")
public class TownController {
    private static final int DEFAULT_RESULT_SIZE = 20;
    private static final String ROOT_DIR = "admin/directories/towns/";
    private static final String LIST_PAGE = ROOT_DIR + "list";
    private static final String REDIRECT_LIST = "redirect:/towns/list/";
    private static final String DETAILS_PAGE = ROOT_DIR + "details";
    private static final String REDIRECT_DETAILS = "redirect:/towns/details/";
    private static final String PAGE_404 = "exception/404";
    private static final String EDIT_PAGE = ROOT_DIR + "edit";
    private final TownService townService;

    @GetMapping("/list")
    public String getTowns(ModelMap modelMap) {
        townService.fillShowAllTownsModelMap(modelMap, DEFAULT_RESULT_SIZE);
        return LIST_PAGE;
    }

    @GetMapping("/details/{id}")
    public String getDetails(ModelMap modelMap, @PathVariable("id") Long townId) {
        townService.fillShowTownDetailsModelMap(modelMap, townId);
        return DETAILS_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(ModelMap modelMap, @PathVariable("id") Long townId) {
        townService.fillShowEditPageModelMap(modelMap, townId);
        return EDIT_PAGE;
    }

    @GetMapping("/add")
    public String getAddPage(ModelMap modelMap) {
        townService.fillShowEditPageModelMap(modelMap);
        return EDIT_PAGE;
    }

    @PostMapping("/save-or-edit")
    public String saveOrUpdate(@Valid @ModelAttribute("town") TownDetailsDto townDto,
                               BindingResult bindingResult, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            townService.fillFailedEditPageModelMap(modelMap);
            return EDIT_PAGE;
        }
        townService.saveOrUpdate(townDto);
        if (townDto.getId() != null)
            return REDIRECT_DETAILS + townDto.getId();
        else
            return REDIRECT_LIST;
    }

    @ExceptionHandler(TownCrudServiceException.class)
    public String handleTownCrudServiceException() {
        return PAGE_404;
    }
}
