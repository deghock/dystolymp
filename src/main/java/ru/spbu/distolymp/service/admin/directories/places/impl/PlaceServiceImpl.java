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
import ru.spbu.distolymp.service.crud.impl.education.PlaceCrudServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Daria Usova
 */
@Log4j
@Service
public class PlaceServiceImpl extends PlaceCrudServiceImpl implements PlaceService {

    public PlaceServiceImpl(PlaceMapper placeMapper, PlaceRepository placeRepository) {
        super(placeMapper, placeRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllPlacesModelMap(ModelMap modelMap, Long divisionId) {
        List<PlaceDto> places = getAllPlacesByDivisionId(divisionId);
        List<Long> placeIdList = new ArrayList<>();

        modelMap.put("places", places);
        modelMap.put("idList", placeIdList);
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

            setNewOrder(place, place.getOrder() - 1);
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

            setNewOrder(place, place.getOrder() + 1);
        } catch (DataAccessException e) {
            log.error("An error occurred while updating place order", e);
            throw new PlaceServiceException();
        }
    }

    private void setNewOrder(Place place, Integer newOrder) {
        Long divisionId = place.getDivision().getId();
        Integer order = place.getOrder();

        Optional<Place> newOrderPlaceOpt = placeRepository.findByDivisionIdAndOrder(divisionId, newOrder);
        if (!newOrderPlaceOpt.isPresent()) {
            return;
        }

        Place newOrderPlace = newOrderPlaceOpt.get();

        newOrderPlace.setOrder(order);
        place.setOrder(newOrder);

        placeRepository.save(newOrderPlace);
        placeRepository.save(place);
    }

    @Override
    @Transactional
    public void deletePlacesById(Long[] ids, Long divisionId) {
        if (ids.length > 0) {
            deletePlacesByIdAndDivision(Arrays.asList(ids), divisionId);
        }
    }

}
