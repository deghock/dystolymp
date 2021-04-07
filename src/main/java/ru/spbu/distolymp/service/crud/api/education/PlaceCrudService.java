package ru.spbu.distolymp.service.crud.api.education;

import ru.spbu.distolymp.dto.entity.education.PlaceDto;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface PlaceCrudService {

    List<PlaceDto> getAllPlaces();

    void saveOrUpdatePlace(PlaceDto placeDto);

    PlaceDto getPlaceById(Long id);

    void deletePlacesByIdAndDivision(List<Long> idList, Long divisionId);

}
