package ru.spbu.distolymp.service.crud.impl.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.users.staff.StaffLoginDto;
import ru.spbu.distolymp.entity.users.Staff;
import ru.spbu.distolymp.exception.crud.users.staff.StaffCrudServiceException;
import ru.spbu.distolymp.mapper.entity.users.staff.StaffLoginMapper;
import ru.spbu.distolymp.repository.users.StaffRepository;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
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
        try {
            return staffRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while getting staff with id=" + id, e);
            throw new StaffCrudServiceException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffLoginDto> getAllStaff() {
        try {
            List<Staff> staffList = (List<Staff>) staffRepository.findAll();
            return staffLoginMapper.toDtoList(staffList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all staff", e);
            throw new StaffCrudServiceException();
        }
    }

}
