package ru.spbu.distolymp.service.admin.directories.places.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.entity.education.PlaceDto;

/**
 * @author Daria Usova
 */
public interface PlaceService {

    void fillShowAllPlacesModelMap(ModelMap modelMap);

    void fillAddNewPlaceModelMap(ModelMap modelMap);

    void fillShowEditPageModelMap(ModelMap modelMap, Long id);

    void saveOrUpdatePlace(PlaceDto placeDto);

    PlaceDto getNewPlaceDto();

    void orderUp(Long placeId);

    void orderDown(Long placeId);

    void deletePlacesById(Long[] ids);

}
