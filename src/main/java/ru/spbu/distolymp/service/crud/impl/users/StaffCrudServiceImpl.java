package ru.spbu.distolymp.service.crud.impl.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.entity.users.Staff;
import ru.spbu.distolymp.repository.users.StaffRepository;
import ru.spbu.distolymp.service.crud.api.users.StaffCrudService;

import javax.persistence.EntityNotFoundException;

/**
 * @author Vladislav Konovalov
 */
@Service
@RequiredArgsConstructor
public class StaffCrudServiceImpl implements StaffCrudService {

    protected final StaffRepository staffRepository;

    @Override
    @Transactional(readOnly = true)
    public Staff getStaffById(Long id) {
        return staffRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Staff with id=" + id + " was not found"));
    }

}
