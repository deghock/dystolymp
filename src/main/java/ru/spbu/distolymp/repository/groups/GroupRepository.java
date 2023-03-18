package ru.spbu.distolymp.repository.groups;

import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.groups.Group;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Long> {
    List<Group> findAll();
    Group findFirstById(Long id);
    List<Group> findAllByListingId(Long listingId);
}
