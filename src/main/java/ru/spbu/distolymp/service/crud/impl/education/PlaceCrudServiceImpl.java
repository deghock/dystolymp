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
    public List<PlaceDto> getAllPlacesByDivisionId(Long id) {
        List<PlaceDto> placeDtoList = new ArrayList<>();

        try {
            List<Place> places = placeRepository.findAllByDivisionIdOrderByOrder(id);
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
            Place place = placeMapper.toEntity(placeDto);
            if (place.getId() == null) {
                setOrder(place);
            }

            place = placeRepository.save(place);

            placeMapper.toDto(place);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving or updating a place", e);
            throw new PlaceCrudException();
        }
    }

    private void setOrder(Place place) {
        Long divisionId = place.getDivision().getId();
        place.setOrder(placeRepository.findMaxOrderByDivisionId(divisionId) + 1);
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
            throw new PlaceCrudException();
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
            throw new PlaceCrudException();
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