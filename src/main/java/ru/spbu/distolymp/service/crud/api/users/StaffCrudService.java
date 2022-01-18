package ru.spbu.distolymp.service.crud.api.users;

import ru.spbu.distolymp.dto.entity.users.staff.StaffLoginDto;
import ru.spbu.distolymp.entity.users.Staff;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface StaffCrudService {
    Staff getStaffByIdOrNull(Long id);
    List<StaffLoginDto> getAllStaff();
}
