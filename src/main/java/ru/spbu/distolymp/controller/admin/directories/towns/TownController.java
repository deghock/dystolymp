package ru.spbu.distolymp.controller.admin.directories.towns;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    private final TownService townService;

    @GetMapping("/list")
    public String getTowns(ModelMap modelMap) {
        townService.fillShowAllTownsModelMap(modelMap, DEFAULT_RESULT_SIZE);
        return LIST_PAGE;
    }

}
