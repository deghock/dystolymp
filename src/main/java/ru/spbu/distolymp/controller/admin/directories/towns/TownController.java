package ru.spbu.distolymp.controller.admin.directories.towns;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spbu.distolymp.exception.crud.geography.TownCrudServiceException;
import ru.spbu.distolymp.service.admin.directories.towns.api.TownService;

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
    private static final String DETAILS_PAGE = ROOT_DIR + "details";
    private static final String PAGE_404 = "exception/404";
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

    @ExceptionHandler(TownCrudServiceException.class)
    public String handleTownCrudServiceException() {
        return PAGE_404;
    }
}
