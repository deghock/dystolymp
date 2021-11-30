package ru.spbu.distolymp.controller.admin.directories.schools;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spbu.distolymp.dto.admin.directories.schools.CreateSchoolDto;
import ru.spbu.distolymp.dto.admin.directories.schools.SchoolFilter;
import ru.spbu.distolymp.dto.entity.education.SchoolDto;
import ru.spbu.distolymp.service.admin.directories.schools.api.SchoolService;

import javax.validation.Valid;

/**
 * @author Maxim Andreev
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/schools")
public class SchoolController {
    private static final int DEFAULT_RESULT_SIZE = 20;
    private static final String ROOT_DIR = "admin/directories/schools/";
    private static final String LIST_PAGE = ROOT_DIR + "list";
    private static final String ADD_PAGE = ROOT_DIR + "add";
    private static final String EDIT_PAGE = ROOT_DIR + "edit";
    private static final String LIST_REDIRECT_PAGE = "redirect:/schools/list";
    private static final String SCHOOLS_TABLE = ROOT_DIR + "schools-table :: #schools-table";
    private static final String DETAILS_PAGE = ROOT_DIR + "details";
    private static final String REDIRECT_DETAILS = "redirect:/schools/details/";
    private static final String SCHOOL_PARAM = "school";
    private static final String SCHOOL_DETAILS = "details";
    private final SchoolService schoolService;

    @GetMapping("/list")
    public String showAllSchools(ModelMap modelMap) {
        schoolService.fillShowAllSchoolsModelMap(modelMap, DEFAULT_RESULT_SIZE);
        return LIST_PAGE;
    }

    @GetMapping("/filter")
    public String getSchoolsBy(SchoolFilter schoolFilter, ModelMap modelMap) {
        modelMap.put("schools", schoolService.getSchoolsBy(schoolFilter));
        return SCHOOLS_TABLE;
    }

    @PostMapping("/delete")
    public String deleteSelectedInstitutesById(@RequestParam(value = "ids") Long[] ids) {
        schoolService.deleteSchoolsById(ids);
        return LIST_REDIRECT_PAGE;
    }

    @GetMapping("/add")
    public String addNewSchool(ModelMap modelMap) {
        schoolService.fillAddNewSchoolModelMap(modelMap);
        return ADD_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") Long schoolId, ModelMap modelMap) {
        schoolService.fillEditPage(modelMap, schoolId);
        modelMap.put(SCHOOL_PARAM, schoolService.getSchoolDtoById(schoolId));
        return EDIT_PAGE;
    }

    @PostMapping("/save-or-edit")
    public String saveOrUpdateCountry(@Valid @ModelAttribute("school") SchoolDto schoolDto,
                                      BindingResult bindingResult, ModelMap modelMap) {

        if (bindingResult.hasErrors()) {
            if (schoolDto.getId() != null) {
                schoolService.fillEditPage(modelMap, schoolDto.getId());
            }
            return EDIT_PAGE;
        }

        schoolService.saveOrUpdate(schoolDto);

        if (schoolDto.getId() != null) {
            return REDIRECT_DETAILS + schoolDto.getId();
        }

        return LIST_REDIRECT_PAGE;
    }

    @GetMapping("/details/{id}")
    public String getCountryDetails(@PathVariable("id") Long schoolId, ModelMap modelMap) {
        modelMap.put(SCHOOL_PARAM, schoolService.getSchoolDetailsById(schoolId));
        return DETAILS_PAGE;
    }
}
