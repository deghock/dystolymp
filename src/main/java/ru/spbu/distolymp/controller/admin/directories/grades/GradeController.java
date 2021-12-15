package ru.spbu.distolymp.controller.admin.directories.grades;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spbu.distolymp.dto.entity.education.grade.GradeDto;
import ru.spbu.distolymp.dto.entity.education.grade.GradeNameDto;
import ru.spbu.distolymp.exception.crud.education.grade.*;
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
    private static final String PAGE_404 = "exception/404";
    private static final String ERROR_PARAM = "error";
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
        if (bindingResult.hasErrors()) { return handleAddNewGradeException(ra); }
        gradeService.saveNewGrade(gradeNameDto);
        return REDIRECT_GRADE_LIST;
    }

    @PostMapping("/rename")
    public String renameGrade(@Valid GradeNameDto gradeNameDto, BindingResult bindingResult,
                              RedirectAttributes ra) {
        if (bindingResult.hasErrors()) { return handleRenameGradeException(ra); }
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

    @ExceptionHandler(AddNewGradeException.class)
    public String handleAddNewGradeException(RedirectAttributes ra) {
        ra.addFlashAttribute(ERROR_PARAM, "Новый класс не был добавлен");
        return REDIRECT_GRADE_LIST;
    }

    @ExceptionHandler(RenameGradeException.class)
    public String handleRenameGradeException(RedirectAttributes ra) {
        ra.addFlashAttribute(ERROR_PARAM, "Имя класса не было изменено");
        return REDIRECT_GRADE_LIST;
    }

    @ExceptionHandler(DeleteGradeException.class)
    public String handleDeleteGradeException(RedirectAttributes ra) {
        ra.addFlashAttribute(ERROR_PARAM, "Класс не был удалён");
        return REDIRECT_GRADE_LIST;
    }

    @ExceptionHandler(UpdateGradeException.class)
    public String handleUpdateGradeException(RedirectAttributes ra) {
        ra.addFlashAttribute(ERROR_PARAM, "Информация не была обновлена");
        return REDIRECT_GRADE_LIST;
    }

    @ExceptionHandler(GradeCrudServiceException.class)
    public String handleGradeCrudServiceException() {
        return PAGE_404;
    }

    // TODO: Implement a link to change the listing

    // TODO: Implement a link to change the staff

}
