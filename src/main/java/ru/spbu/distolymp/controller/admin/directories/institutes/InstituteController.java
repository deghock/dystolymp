package ru.spbu.distolymp.controller.admin.directories.institutes;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spbu.distolymp.dto.entity.education.InstituteDto;
import ru.spbu.distolymp.repository.education.InstituteRepository;
import ru.spbu.distolymp.service.admin.directories.institutes.api.InstituteService;

import javax.validation.Valid;


@Log4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/institutes")
public class InstituteController {

    private static final String ROOT_DIR = "admin/directories/institutes/";
    private static final String LIST_PAGE = ROOT_DIR + "list";
    private static final String EDIT_PAGE = ROOT_DIR + "edit";
    private static final String LIST_REDIRECT_PAGE = "redirect:/institutes/list";

    private final InstituteService instituteService;

    @GetMapping("/list")
    public String showAllInstitutes(ModelMap modelMap) {
        instituteService.fillShowAllInstitutesModelMap(modelMap);
        return LIST_PAGE;
    }

    @GetMapping("/add")
    public String addNewInstitute(ModelMap modelMap) {
        instituteService.fillAddNewInstituteModelMap(modelMap);
        return EDIT_PAGE;
    }

    @PostMapping("/save-or-edit")
    public String saveOrEdit(@Valid @ModelAttribute("institute") InstituteDto instituteDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return EDIT_PAGE;
        }
        instituteService.saveOrEdit(instituteDto);
        return LIST_REDIRECT_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable("id") Long id, ModelMap modelMap) {
        instituteService.fillShowEditPageModelMap(modelMap, id);
        return EDIT_PAGE;
    }

    @GetMapping("/order/up/{id}")
    public String orderUp(@PathVariable("id") Long instituteId) {
        instituteService.orderUp(instituteId);
        return LIST_REDIRECT_PAGE;
    }

    @GetMapping("/order/down/{id}")
    public String orderDown(@PathVariable("id") Long instituteId) {
        instituteService.orderDown(instituteId);
        return LIST_REDIRECT_PAGE;
    }


    @PostMapping("/delete")
    public String deleteSelectedInstitutesById(@RequestParam(value = "ids") Long[] ids) {
        instituteService.deleteInstitutesById(ids);
        return LIST_REDIRECT_PAGE;
    }

}
