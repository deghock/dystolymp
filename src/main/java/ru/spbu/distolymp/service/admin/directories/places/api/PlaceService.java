package ru.spbu.distolymp.service.admin.directories.places.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.entity.education.PlaceDto;

/**
 * @author Daria Usova
 */
public interface PlaceService {

    void fillShowAllPlacesModelMap(ModelMap modelMap, Long divisionId);

    void fillAddNewPlaceModelMap(ModelMap modelMap, Long divisionId);

    void fillShowEditPageModelMap(ModelMap modelMap, Long id);

    void saveOrEditPlace(PlaceDto placeDto);

    PlaceDto getNewPlaceDto(Long divisionId);

    PlaceDto getPlaceDtoById(Long id);

    void orderUp(Long placeId);

    void orderDown(Long placeId);

    void deletePlacesById(Long[] ids, Long divisionId);

}
