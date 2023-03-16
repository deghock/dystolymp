package ru.spbu.distolymp.controller.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spbu.distolymp.dto.registration.UserRegistrationDto;
import ru.spbu.distolymp.service.admin.registration.api.UserRegistrationService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Daria Usova
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping("/user")
    public String showRegistrationPage(ModelMap modelMap) {
        userRegistrationService.fillShowRegistrationPageModelMap(modelMap);
        return "registration/user";
    }

    @PostMapping("/user")
    public String registerUser(@Valid @ModelAttribute("userRegistrationDto") UserRegistrationDto dto,
                               BindingResult bindingResult) {
        userRegistrationService.registerUser(dto);
        return "registration/success";
    }

    @ResponseBody
    @GetMapping(value = "/towns", produces = "application/json")
    public List<String> getTownsInRegion(@RequestParam(value = "regionId", required = false) Long regionId,
                                         @RequestParam(value = "countryId", required = false) Long countryId) {
        return userRegistrationService.getTownsByRegionOrCountry(regionId, countryId);
    }

}
