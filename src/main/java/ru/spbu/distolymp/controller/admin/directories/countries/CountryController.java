package ru.spbu.distolymp.controller.admin.directories.countries;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryFilter;
import ru.spbu.distolymp.dto.entity.geography.country.CountryDto;
import ru.spbu.distolymp.exception.admin.directories.countries.CountryServiceException;
import ru.spbu.distolymp.exception.crud.geography.CountryCrudServiceException;
import ru.spbu.distolymp.service.admin.directories.countries.api.CountryService;

import javax.validation.Valid;

/**
 * @author Daria Usova
 */
@Log4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;
    private static final int DEFAULT_RESULT_SIZE = 20;
    private static final String ROOT = "admin/directories/countries/";
    private static final String EDIT_PAGE = ROOT + "edit";
    private static final String LIST_PAGE = ROOT + "list";
    private static final String REDIRECT_LIST = "redirect:/countries/list";
    private static final String REDIRECT_DETAILS = "redirect:/countries/details/";
    private static final String COUNTRY_PARAM = "country";

    @GetMapping("/list")
    public String getCountries(ModelMap modelMap) {
        countryService.fillShowAllCountriesModelMap(modelMap, DEFAULT_RESULT_SIZE);
        return LIST_PAGE;
    }

    @GetMapping("/filter")
    public String getCountries(CountryFilter countryFilter, ModelMap modelMap) {
        modelMap.put("countries", countryService.getCountriesBy(countryFilter));
        return "admin/directories/countries/countries-table :: #countries-table";
    }

    @GetMapping("/add")
    public String addNewCountry(ModelMap modelMap) {
        modelMap.put(COUNTRY_PARAM, countryService.getNewCountryDto());
        return EDIT_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") Long countryId, ModelMap modelMap) {
        modelMap.put(COUNTRY_PARAM, countryService.getCountryById(countryId));
        return EDIT_PAGE;
    }

    @PostMapping("/save-or-edit")
    public String saveOrUpdateCountry(@Valid @ModelAttribute("country") CountryDto countryDto,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return EDIT_PAGE;
        }

        countryService.saveOrUpdate(countryDto);

        if (countryDto.getId() != null) {
            return REDIRECT_DETAILS + countryDto.getId();
        }

        return REDIRECT_LIST;
    }

    @GetMapping("/details/{id}")
    public String getCountryDetails(@PathVariable("id") Long countryId, ModelMap modelMap) {
        modelMap.put(COUNTRY_PARAM, countryService.getCountryDetailsById(countryId));
        return "admin/directories/countries/detail";
    }

    @PostMapping("/delete")
    public String deleteSelectedPlacesById(@RequestParam(value = "ids") Long[] ids) {
        countryService.deleteCountriesWithIdIn(ids);
        return REDIRECT_LIST;
    }

    @ExceptionHandler({CountryCrudServiceException.class, CountryServiceException.class})
    public String handleCountryException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Произошла ошибка. Пожалуйста, попробуйте повторить операцию позже.");
        return REDIRECT_LIST;
    }

}
