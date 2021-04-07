package ru.spbu.distolymp.repository.education;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.education.Place;

import java.util.List;
import java.util.Optional;

/**
 * @author Daria Usova
 */
public interface PlaceRepository extends CrudRepository<Place, Long> {

    List<Place> findAllByDivisionIdOrderByOrder(Long id);

    @Query("select coalesce(max(p.order), 0) from Place p where p.division.id=?1")
    Integer findMaxOrderByDivisionId(Long id);

    List<Place> findByDivisionIdAndOrderBetween(Long divisionId, Integer startOrder, Integer endOrder);

    Optional<Place> findByDivisionIdAndOrder(Long divisionId, Integer order);

    void deletePlacesByIdInAndDivisionId(List<Long> idList, Long divisionId);

}
