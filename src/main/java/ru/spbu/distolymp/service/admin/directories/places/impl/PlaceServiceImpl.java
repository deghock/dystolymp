package ru.spbu.distolymp.service.admin.directories.places.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.entity.education.PlaceDto;
import ru.spbu.distolymp.entity.education.Place;
import ru.spbu.distolymp.exception.admin.directories.places.PlaceServiceException;
import ru.spbu.distolymp.mapper.entity.education.PlaceMapper;
import ru.spbu.distolymp.repository.education.PlaceRepository;
import ru.spbu.distolymp.service.admin.directories.places.api.PlaceService;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.impl.education.PlaceCrudServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Daria Usova
 */
@Log4j
@Service
public class PlaceServiceImpl extends PlaceCrudServiceImpl implements PlaceService {

    public PlaceServiceImpl(PlaceMapper placeMapper, PlaceRepository placeRepository,
                            DivisionCrudService divisionCrudService) {
        super(placeMapper, placeRepository, divisionCrudService);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllPlacesModelMap(ModelMap modelMap) {
        List<PlaceDto> places = getAllPlaces();
        List<Long> placeIdList = new ArrayList<>();

        modelMap.put("places", places);
        modelMap.put("idList", placeIdList);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillAddNewPlaceModelMap(ModelMap modelMap) {
        modelMap.put("place", getNewPlaceDto());
        modelMap.put("maxOrder", findMaxOrder() + 1);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditPageModelMap(ModelMap modelMap, Long id) {
        PlaceDto place = findPlaceDtoById(id).orElseThrow(PlaceServiceException::new);

        modelMap.put("place", place);
        modelMap.put("maxOrder", findMaxOrder());
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceDto getNewPlaceDto() {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setVisible(true);
        placeDto.setOrder(findMaxOrder() + 1);

        return placeDto;
    }

    @Override
    @Transactional
    public void orderUp(Long placeId) {
        try {
            Place place = placeRepository.findById(placeId)
                    .orElseThrow(EntityNotFoundException::new);

            if (place.getOrder() == 1) return;
            setNewOrder(place, place.getOrder(), place.getOrder() - 1);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while updating place order", e);
            throw new PlaceServiceException();
        }
    }

    @Override
    @Transactional
    public void orderDown(Long placeId) {
        try {
            Place place = placeRepository.findById(placeId)
                    .orElseThrow(EntityNotFoundException::new);
            int maxOrder = findMaxOrder();

            if (place.getOrder() == maxOrder) return;
            setNewOrder(place, place.getOrder(), place.getOrder() + 1);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while updating place order", e);
            throw new PlaceServiceException();
        }
    }

    @Override
    @Transactional
    public void deletePlacesById(Long[] ids) {
        if (ids.length > 0) {
            deletePlacesByIdIn(Arrays.asList(ids));
        }
    }

}