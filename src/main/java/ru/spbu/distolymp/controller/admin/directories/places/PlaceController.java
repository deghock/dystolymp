package ru.spbu.distolymp.controller.admin.directories.places;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spbu.distolymp.dto.entity.education.PlaceDto;
import ru.spbu.distolymp.exception.admin.directories.places.PlaceServiceException;
import ru.spbu.distolymp.exception.crud.education.PlaceCrudServiceException;
import ru.spbu.distolymp.service.admin.directories.places.api.PlaceService;

import javax.validation.Valid;

/**
 * @author Daria Usova
 */
@Log4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {

    private static final String ROOT_DIR = "admin/directories/places/";
    private static final String LIST_PAGE = ROOT_DIR + "list";
    private static final String EDIT_PAGE = ROOT_DIR + "edit";
    private static final String ENTRY_REDIRECT_PAGE = "redirect:/division/entry";
    private static final String LIST_REDIRECT_PAGE = "redirect:/places/list";

    private final PlaceService placeService;

    @GetMapping("/list")
    public String showAllPlaces(ModelMap modelMap,
                                @SessionAttribute(value = "idDivision", required = false) Long id) {
        if (id == null) {
            return ENTRY_REDIRECT_PAGE;
        }

        placeService.fillShowAllPlacesModelMap(modelMap, id);
        return LIST_PAGE;
    }

    @GetMapping("/add")
    public String addNewPlace(ModelMap modelMap, @SessionAttribute(value = "idDivision", required = false) Long id) {
        if (id == null) {
            return ENTRY_REDIRECT_PAGE;
        }

        modelMap.put("place", placeService.getNewPlaceDto(id));
        return EDIT_PAGE;
    }

    @PostMapping("/save-or-edit")
    public String saveOrEdit(@Valid @ModelAttribute("place") PlaceDto placeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return EDIT_PAGE;
        }

        placeService.saveOrEditPlace(placeDto);
        return LIST_REDIRECT_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.put("place", placeService.getPlaceDtoById(id));
        return EDIT_PAGE;
    }

    @GetMapping("/order/up/{id}")
    public String orderUp(@PathVariable("id") Long placeId) {
        placeService.orderUp(placeId);
        return LIST_REDIRECT_PAGE;
    }

    @GetMapping("/order/down/{id}")
    public String orderDown(@PathVariable("id") Long placeId) {
        placeService.orderDown(placeId);
        return LIST_REDIRECT_PAGE;
    }

    @PostMapping("/delete")
    public String deleteSelectedPlacesById(@RequestParam(value = "ids") Long[] ids,
                                           @SessionAttribute(value = "idDivision", required = false) Long divisionId) {
        if (divisionId == null) {
            return ENTRY_REDIRECT_PAGE;
        }

        placeService.deletePlacesById(ids, divisionId);
        return LIST_REDIRECT_PAGE;
    }

    @ExceptionHandler({PlaceServiceException.class, PlaceCrudServiceException.class})
    public String handlePlaceCrudException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Произошла ошибка. Пожалуйста, попробуйте повторить операцию позже.");
        return LIST_REDIRECT_PAGE;
    }

}
