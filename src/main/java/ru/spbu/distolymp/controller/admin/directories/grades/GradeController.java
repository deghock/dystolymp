package ru.spbu.distolymp.controller.admin.directories.grades;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.spbu.distolymp.service.admin.directories.grades.api.GradeService;

/**
 * @author Vladislav Konovalov
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/grades")
public class GradeController {

    private static final String ROOT_DIR = "admin/directories/grades/";
    private static final String LIST_PAGE = ROOT_DIR + "list";
    private static final String ENTRY_REDIRECT_PAGE = "redirect:/division/entry";

    private final GradeService gradeService;

    @GetMapping("/list")
    public String showAllGrades(ModelMap modelMap,
                                 @SessionAttribute(name = "idDivision", required = false)
                                         Long divisionId) {
        if (divisionId == null) return ENTRY_REDIRECT_PAGE;
        gradeService.fillShowAllGradesModelMap(modelMap, divisionId);
        return LIST_PAGE;
    }

}
