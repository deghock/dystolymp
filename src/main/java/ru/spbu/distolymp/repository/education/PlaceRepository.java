package ru.spbu.distolymp.repository.education;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.education.Place;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface PlaceRepository extends CrudRepository<Place, Long> {

    List<Place> findAllByOrderByOrder();

    @Query("select coalesce(max(p.order), 0) from Place p where p.division.id=?1")
    Integer findMaxOrderByDivisionId(Long id);

    List<Place> findByDivisionIdAndOrderBetween(Long divisionId, Integer startOrder, Integer endOrder);

    void deletePlacesByIdIn(List<Long> idList);

}
