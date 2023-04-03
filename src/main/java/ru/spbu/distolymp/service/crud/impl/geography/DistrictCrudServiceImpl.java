package ru.spbu.distolymp.service.crud.impl.geography;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.entity.geography.District;
import ru.spbu.distolymp.repository.geography.DistrictRepository;
import ru.spbu.distolymp.service.crud.api.geography.DistrictCrudService;

import javax.persistence.EntityNotFoundException;

/**
 * @author Maxim Andreev
 */
@Log4j
@Service
@RequiredArgsConstructor
public class DistrictCrudServiceImpl implements DistrictCrudService {

    private final DistrictRepository districtRepository;

    @Override
    @Transactional(readOnly = true)
    public District getDistrictById(Long id) {
        if (id == null || id == 0) return null;
        return districtRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("District with id=" + id + " was not found"));
    }
}
