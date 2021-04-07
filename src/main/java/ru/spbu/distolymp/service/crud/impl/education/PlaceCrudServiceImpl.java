package ru.spbu.distolymp.service.crud.impl.education;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.education.PlaceDto;
import ru.spbu.distolymp.entity.education.Place;
import ru.spbu.distolymp.exception.crud.education.PlaceCrudServiceException;
import ru.spbu.distolymp.mapper.entity.education.PlaceMapper;
import ru.spbu.distolymp.repository.education.PlaceRepository;
import ru.spbu.distolymp.service.crud.api.education.PlaceCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daria Usova
 */
@Log4j
@Service
@RequiredArgsConstructor
public class PlaceCrudServiceImpl implements PlaceCrudService {

    private final PlaceMapper placeMapper;
    protected final PlaceRepository placeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PlaceDto> getAllPlaces() {
        List<PlaceDto> placeDtoList = new ArrayList<>();

        try {
            List<Place> places = placeRepository.findAllByOrderByOrder();
            placeDtoList = placeMapper.toDtoList(places);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all places", e);
        }

        return placeDtoList;
    }

    @Override
    @Transactional
    public void saveOrUpdatePlace(PlaceDto placeDto) {
        try {
            Integer oldOrder;
            if (placeDto.getId() == null) {
                oldOrder = placeRepository.findMaxOrderByDivisionId(placeDto.getDivisionId()) + 1;
            }else{
                oldOrder = placeRepository.findById(placeDto.getId()).get().getOrder();
            }
            Place place = placeMapper.toEntity(placeDto);
            setNewOrder(place, oldOrder, place.getOrder());
        } catch (DataAccessException e) {
            log.error("An error occurred while saving or updating a place", e);
            throw new PlaceCrudServiceException();
        }
    }

    protected void setNewOrder(Place place, Integer oldOrder, Integer newOrder) {
        Long divisionId = place.getDivision().getId();
        List<Place> placeToSave;
        Place plc;
        if(oldOrder > newOrder) {
            placeToSave = placeRepository.findByDivisionIdAndOrderBetween(divisionId, newOrder, oldOrder);
            for(Place p: placeToSave) {
                p.setOrder(p.getOrder() + 1);
            }
        } else if(oldOrder < newOrder) {
            placeToSave = placeRepository.findByDivisionIdAndOrderBetween(divisionId, oldOrder, newOrder);
            for(Place p: placeToSave) {
                p.setOrder(p.getOrder() - 1);
            }
        }
        place.setOrder(newOrder);
        placeRepository.save(place);
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceDto getPlaceById(Long id) {
        try {
            Place place = placeRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
            return placeMapper.toDto(place);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting place by id=" + id, e);
            throw new PlaceCrudServiceException();
        }
    }

    @Override
    @Transactional
    public void deletePlacesByIdAndDivision(List<Long> idList, Long divisionId) {
        try {
            placeRepository.deletePlacesByIdInAndDivisionId(idList, divisionId);
            updatePlaceOrder(divisionId);
        } catch (DataAccessException e) {
            log.error("An error occurred while deleting places by id list", e);
            throw new PlaceCrudServiceException();
        }
    }

    private void updatePlaceOrder(Long divisionId) {
        List<Place> places = placeRepository.findAllByDivisionIdOrderByOrder(divisionId);
        for (int i = 0; i < places.size(); i++) {
            Place place = places.get(i);
            place.setOrder(i + 1);
        }

        placeRepository.saveAll(places);
    }

}