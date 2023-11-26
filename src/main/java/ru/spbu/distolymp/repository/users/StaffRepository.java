package ru.spbu.distolymp.repository.users;

import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.users.Staff;

import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public interface StaffRepository extends CrudRepository<Staff, Long> {
    Optional<Staff> findByLogin(String login);
}
