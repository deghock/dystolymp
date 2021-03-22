package ru.spbu.distolymp.controller.admin.directories.grades;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeNameDto;
import ru.spbu.distolymp.exception.crud.lists.grade.AddNewGradeException;
import ru.spbu.distolymp.exception.crud.lists.grade.RenameGradeException;
import ru.spbu.distolymp.service.admin.directories.grades.api.GradeService;

import javax.validation.Valid;

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
    private static final String REDIRECT_GRADE_LIST = "redirect:/grades/list";

    private final GradeService gradeService;

    @GetMapping("/list")
    public String showAllGrades(ModelMap modelMap,
                                 @SessionAttribute(name = "idDivision", required = false)
                                         Long divisionId) {
        if (divisionId == null) return ENTRY_REDIRECT_PAGE;
        gradeService.fillShowAllGradesModelMap(modelMap, divisionId);
        return LIST_PAGE;
    }

    @GetMapping("/delete/{id}")
    public String deleteGrade(@PathVariable("id") Long id,
                              @SessionAttribute(value = "idDivision", required = false)
                                      Long divisionId) {
        gradeService.deleteGradeById(id, divisionId);
        return REDIRECT_GRADE_LIST;
    }

    @PostMapping("/add")
    public String addNewGrade(@Valid GradeNameDto gradeNameDto, BindingResult bindingResult,
                              RedirectAttributes ra) {
        if (bindingResult.hasErrors()) { return handleAddNewGradeException(ra); }
        gradeService.addNewGrade(gradeNameDto);
        return REDIRECT_GRADE_LIST;
    }

    @ExceptionHandler(AddNewGradeException.class)
    public String handleAddNewGradeException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Новый класс не был добавлен");
        return REDIRECT_GRADE_LIST;
    }

    @PostMapping("/rename")
    public String renameGrade(@Valid GradeNameDto gradeNameDto, BindingResult bindingResult,
                              RedirectAttributes ra) {
        if (bindingResult.hasErrors()) { return handleRenameGradeException(ra); }
        gradeService.renameGrade(gradeNameDto);
        return REDIRECT_GRADE_LIST;
    }

    @ExceptionHandler(RenameGradeException.class)
    public String handleRenameGradeException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Имя класса не было изменено");
        return REDIRECT_GRADE_LIST;
    }

}
