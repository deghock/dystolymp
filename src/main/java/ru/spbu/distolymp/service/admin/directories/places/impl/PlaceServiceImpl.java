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
        modelMap.put("place", getPlaceDtoById(id));
        Long divId = placeRepository.findById(id).get().getDivision().getId();
        modelMap.put("maxOrder", placeRepository.findMaxOrderByDivisionId(divId));
    }

    @Override
    @Transactional
    public void saveOrEditPlace(PlaceDto placeDto) {
        saveOrUpdatePlace(placeDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceDto getNewPlaceDto(Long divisionId) {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setVisible(true);
        placeDto.setDivisionId(divisionId);

        try {
            Integer order = placeRepository.findMaxOrderByDivisionId(divisionId) + 1;
            placeDto.setOrder(order);
        } catch (DataAccessException e) {
            log.error("An error occurred while finding max order by division id", e);
            throw new PlaceServiceException();
        }

        return placeDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceDto getPlaceDtoById(Long id) {
        return getPlaceById(id);
    }

    @Override
    @Transactional
    public void orderUp(Long placeId) {
        try {
            Place place = placeRepository.findById(placeId)
                    .orElseThrow(EntityNotFoundException::new);

            setNewOrder(place, place.getOrder(), place.getOrder() - 1);
        } catch (DataAccessException e) {
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

            setNewOrder(place, place.getOrder(), place.getOrder() + 1);
        } catch (DataAccessException e) {
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
