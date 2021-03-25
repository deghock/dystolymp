package ru.spbu.distolymp.service.crud.impl.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.staff.StaffLoginDto;
import ru.spbu.distolymp.entity.lists.Division;
import ru.spbu.distolymp.entity.users.Staff;
import ru.spbu.distolymp.exception.crud.users.staff.StaffCrudServiceException;
import ru.spbu.distolymp.mapper.admin.directories.users.staff.StaffLoginMapper;
import ru.spbu.distolymp.repository.users.StaffRepository;
import ru.spbu.distolymp.service.crud.api.lists.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.users.StaffCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@RequiredArgsConstructor
public class StaffCrudServiceImpl implements StaffCrudService {

    private final StaffLoginMapper staffLoginMapper;
    private final DivisionCrudService divisionCrudService;
    protected final StaffRepository staffRepository;

    @Override
    @Transactional(readOnly = true)
    public Staff getStaffById(Long id) {
        if (id == null) return null;
        return staffRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Staff with id=" + id + " was not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffLoginDto> getAllStaffByDivisionId(Long divisionId) {
        List<StaffLoginDto> staffListDto;
        try {
            Division division = divisionCrudService.getDivisionById(divisionId);
            List<Staff> staffList = division.getStaffList();
            staffListDto = staffLoginMapper.toDtoList(staffList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all staff", e);
            throw new StaffCrudServiceException();
        }
        return staffListDto;
    }

}
