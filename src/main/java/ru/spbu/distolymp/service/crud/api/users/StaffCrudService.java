package ru.spbu.distolymp.service.crud.api.users;

import ru.spbu.distolymp.dto.admin.directories.staff.StaffLoginDto;
import ru.spbu.distolymp.entity.users.Staff;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface StaffCrudService {

    Staff getStaffById(Long id);

    List<StaffLoginDto> getAllStaffByDivisionId(Long divisionId);

}
