package ru.spbu.distolymp.service.admin.registration.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.registration.UserRegistrationDto;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface UserRegistrationService {

    void fillShowRegistrationPageModelMap(ModelMap modelMap);

    List<String> getTownsByRegionOrCountry(Long regionId, Long countryId);

    void registerUser(UserRegistrationDto userRegistrationDto);

}
