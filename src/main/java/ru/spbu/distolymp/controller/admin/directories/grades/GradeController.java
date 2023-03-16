package ru.spbu.distolymp.controller.admin.directories.grades;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spbu.distolymp.dto.entity.education.grade.GradeDto;
import ru.spbu.distolymp.dto.entity.education.grade.GradeNameDto;
import ru.spbu.distolymp.exception.common.TechnicalException;
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
    private static final String REDIRECT_GRADE_LIST = "redirect:/grades/list";
    private static final String EDIT_PAGE = ROOT_DIR + "edit";
    private final GradeService gradeService;

    @GetMapping("/list")
    public String showAllGrades(ModelMap modelMap) {
        gradeService.fillShowAllGradesModelMap(modelMap);
        return LIST_PAGE;
    }

    @GetMapping("/delete/{id}")
    public String deleteGrade(@PathVariable("id") Long id) {
        gradeService.deleteGradeById(id);
        return REDIRECT_GRADE_LIST;
    }

    @PostMapping("/add")
    public String addNewGrade(@Valid GradeNameDto gradeNameDto, BindingResult bindingResult,
                              RedirectAttributes ra) {
        if (bindingResult.hasErrors()) { return handleTechnicalException(ra); }
        gradeService.saveNewGrade(gradeNameDto);
        return REDIRECT_GRADE_LIST;
    }

    @PostMapping("/rename")
    public String renameGrade(@Valid GradeNameDto gradeNameDto, BindingResult bindingResult,
                              RedirectAttributes ra) {
        if (bindingResult.hasErrors()) { return handleTechnicalException(ra); }
        gradeService.renameGrade(gradeNameDto);
        return REDIRECT_GRADE_LIST;
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable("id") Long id, ModelMap modelMap) {
        gradeService.fillShowEditPageModelMap(id, modelMap);
        return EDIT_PAGE;
    }

    @PostMapping("/update")
    public String updateGrade(@Valid @ModelAttribute("grade") GradeDto gradeDto,
                              BindingResult bindingResult, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            gradeService.fillUpdateFailedModelMap(modelMap);
            return EDIT_PAGE;
        }

        gradeService.updateGrade(gradeDto);
        return REDIRECT_GRADE_LIST;
    }

    @GetMapping("/open_registration")
    public String openRegistration(ModelMap modelMap) {
        gradeService.fillShowChangeRegistrationStatus(modelMap, "open");
        return REDIRECT_GRADE_LIST;
    }

    @GetMapping("/close_registration")
    public String closeRegistration(ModelMap modelMap) {
        gradeService.fillShowChangeRegistrationStatus(modelMap, "close");
        return REDIRECT_GRADE_LIST;
    }

    @ExceptionHandler(TechnicalException.class)
    public String handleTechnicalException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Произошла ошибка. Пожалуйста, попробуйте повторить операцию позже.");
        return REDIRECT_GRADE_LIST;
    }
}
