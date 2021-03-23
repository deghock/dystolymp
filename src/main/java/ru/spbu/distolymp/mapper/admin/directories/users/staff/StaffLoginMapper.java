package ru.spbu.distolymp.mapper.admin.directories.users.staff;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.admin.directories.users.staff.StaffLoginDto;
import ru.spbu.distolymp.entity.users.Staff;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface StaffLoginMapper {

    List<StaffLoginDto> toDtoList(List<Staff> staffList);

    StaffLoginDto toDto(Staff staff);

    Staff toEntity(StaffLoginDto staffLoginDto);

}
