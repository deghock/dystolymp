package ru.spbu.distolymp.service.crud.api.education;

import ru.spbu.distolymp.dto.entity.education.PlaceDto;

import java.util.List;
import java.util.Optional;

/**
 * @author Daria Usova
 */
public interface PlaceCrudService {

    List<PlaceDto> getAllPlaces();

    void saveOrUpdatePlace(PlaceDto placeDto);

    void save(PlaceDto placeDto);

    void update(PlaceDto placeDto);

    Optional<PlaceDto> findPlaceDtoById(Long id);

    void deletePlacesByIdIn(List<Long> idList);

    Integer findMaxOrder();

}
