package ru.spbu.distolymp.service.crud.impl.education;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.education.PlaceDto;
import ru.spbu.distolymp.entity.division.Division;
import ru.spbu.distolymp.entity.education.Place;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.exception.crud.education.PlaceCrudServiceException;
import ru.spbu.distolymp.mapper.entity.education.PlaceMapper;
import ru.spbu.distolymp.repository.education.PlaceRepository;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.education.PlaceCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Daria Usova
 */
@Log4j
@Service
@RequiredArgsConstructor
public class PlaceCrudServiceImpl implements PlaceCrudService {

    private final PlaceMapper placeMapper;
    protected final PlaceRepository placeRepository;
    private final DivisionCrudService divisionCrudService;

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
        if (placeDto.getId() == null) {
            save(placeDto);
        } else {
            update(placeDto);
        }
    }

    @Override
    @Transactional
    public void save(PlaceDto placeDto) {
        try {
            tryToSavePlace(placeDto);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving place", e);
            throw new PlaceCrudServiceException();
        }
    }

    private void tryToSavePlace(PlaceDto placeDto) {
        Place place = placeMapper.toEntity(placeDto);
        Division division = divisionCrudService.getAnyDivision();

        place.setDivision(division);

        setNewOrder(place, placeRepository.findMaxOrder() + 1, place.getOrder());
        placeRepository.save(place);
    }

    @Override
    @Transactional
    public void update(PlaceDto placeDto) {
        try {
            tryToUpdatePlace(placeDto);
        } catch (DataAccessException e) {
            log.error("An error occurred while updating place", e);
            throw new PlaceCrudServiceException();
        }
    }

    private void tryToUpdatePlace(PlaceDto placeDto) {
        Place place = placeRepository.findById(placeDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        place.setVisible( Visible.getVisible(placeDto.isVisible()) );
        place.setName( placeDto.getName() );

        setNewOrder(place, place.getOrder(), placeDto.getOrder());
    }

    protected void setNewOrder(Place place, Integer oldOrder, Integer newOrder) {
        List<Place> placeToUpdate;
        if (oldOrder > newOrder) {
            placeToUpdate = placeRepository.findByOrderBetween(newOrder, oldOrder);
            for (Place p : placeToUpdate) {
                p.setOrder(p.getOrder() + 1);
            }
        } else if (oldOrder < newOrder) {
            placeToUpdate = placeRepository.findByOrderBetween(oldOrder, newOrder);
            for (Place p : placeToUpdate) {
                p.setOrder(p.getOrder() - 1);
            }
        }
        place.setOrder(newOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlaceDto> findPlaceDtoById(Long id) {
        try {
            return placeRepository.findById(id)
                    .map(placeMapper::toDto);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting place by id=" + id, e);
            throw new PlaceCrudServiceException();
        }
    }

    @Override
    @Transactional
    public void deletePlacesByIdIn(List<Long> idList) {
        try {
            placeRepository.deletePlacesByIdIn(idList);
            updatePlaceOrder();
        } catch (DataAccessException e) {
            log.error("An error occurred while deleting places by id list", e);
            throw new PlaceCrudServiceException();
        }
    }

    private void updatePlaceOrder() {
        List<Place> places = placeRepository.findAllByOrderByOrder();
        for (int i = 0; i < places.size(); i++) {
            Place place = places.get(i);
            place.setOrder(i + 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer findMaxOrder() {
        try {
            return placeRepository.findMaxOrder();
        } catch (DataAccessException e) {
            log.error("An error occurred while getting max place order", e);
            throw new PlaceCrudServiceException();
        }
    }
}