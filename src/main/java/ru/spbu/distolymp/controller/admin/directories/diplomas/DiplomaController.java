package ru.spbu.distolymp.controller.admin.directories.diplomas;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spbu.distolymp.dto.admin.directories.diplomas.NewDiplomaTypeDto;
import ru.spbu.distolymp.exception.admin.directories.diplomas.DiplomaTypeServiceException;
import ru.spbu.distolymp.exception.crud.diploma.DiplomaTypeCrudServiceException;
import ru.spbu.distolymp.service.admin.directories.diplomas.api.DiplomaTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Daria Usova
 */
@Log4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/diplomas")
public class DiplomaController {

    private final DiplomaTypeService diplomaTypeService;

    private static final String ROOT = "admin/directories/diplomas/";
    private static final String LIST_PAGE = ROOT + "list";
    private static final String ADD_PAGE = ROOT + "add";
    private static final String REDIRECT_LIST = "redirect:/diplomas/list";

    @GetMapping("/list")
    public String showAllDiplomas(ModelMap modelMap) {
        diplomaTypeService.fillShowAllDiplomaTypesModelMap(modelMap);
        return LIST_PAGE;
    }

    @GetMapping("/add")
    public String showAddNewDiplomaTypePage(ModelMap modelMap) {
        modelMap.put("diploma", diplomaTypeService.getNewDiplomaType());
        return ADD_PAGE;
    }

    @PostMapping("/add")
    public String addDiploma(@Valid @ModelAttribute("diploma") NewDiplomaTypeDto newDiplomaType,
                             BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ADD_PAGE;
        }

        diplomaTypeService.addNewDiplomaType(newDiplomaType, request);
        return REDIRECT_LIST;
    }

    @ExceptionHandler({DiplomaTypeServiceException.class, DiplomaTypeCrudServiceException.class})
    public String handleDiplomaTypeException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Произошла ошибка. Пожалуйста, попробуйте повторить операцию позже.");
        return REDIRECT_LIST;
    }

}
