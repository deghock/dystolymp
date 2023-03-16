package ru.spbu.distolymp.repository.groups;

import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.groups.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
