package ru.spbu.distolymp.service.crud.api.users;

import ru.spbu.distolymp.entity.users.Staff;

/**
 * @author Vladislav Konovalov
 */
public interface StaffCrudService {

    Staff getStaffById(Long id);

}
