package ru.spbu.distolymp.service.crud.impl.education;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.education.InstituteDto;
import ru.spbu.distolymp.entity.education.Institute;
import ru.spbu.distolymp.exception.crud.education.InstituteCrudException;
import ru.spbu.distolymp.mapper.entity.education.InstituteMapper;
import ru.spbu.distolymp.repository.education.InstituteRepository;
import ru.spbu.distolymp.service.crud.api.education.InstituteCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Log4j
@Service
@RequiredArgsConstructor
public class InstituteCrudServiceImpl implements InstituteCrudService {

    private final InstituteMapper instituteMapper;
    protected final InstituteRepository instituteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InstituteDto> getAllInstitutes() {
        List<InstituteDto> instituteDtoList = new ArrayList<>();

        try {
            List<Institute> institutes = instituteRepository.findAllByOrderByOrder();
            instituteDtoList = instituteMapper.toDtoList(institutes);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all institutes", e);
        }

        return instituteDtoList;
    }

    @Override
    @Transactional
    public void saveOrUpdateInstitute(InstituteDto instituteDto) {
        try {
            Institute institute = instituteMapper.toEntity(instituteDto);
            if (institute.getId() == null) {
                setOrder(institute);
            }

            instituteRepository.save(institute);

        } catch (DataAccessException e) {
            log.error("An error occurred while saving or updating an institute", e);
            throw new InstituteCrudException();
        }
    }

    private void setOrder(Institute institute) {
        institute.setOrder(instituteRepository.findMaxOrder() + 1);
    }

    @Override
    @Transactional(readOnly = true)
    public InstituteDto getInstituteById(Long id) {
        try {
            Institute institute = instituteRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
            return instituteMapper.toDto(institute);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting institute by id=" + id, e);
            throw new InstituteCrudException();
        }
    }

    @Override
    @Transactional
    public void deleteInstitutesById(List<Long> idList) {
        try {
            instituteRepository.deleteInstitutesByIdIn(idList);
            updateInstituteOrder();
        } catch (DataAccessException e) {
            log.error("An error occurred while deleting institutes by id list", e);
            throw new InstituteCrudException();
        }
    }

    private void updateInstituteOrder() {
        List<Institute> institutes = instituteRepository.findAllByOrderByOrder();
        for (int i = 0; i < institutes.size(); i++) {
            Institute institute = institutes.get(i);
            institute.setOrder(i + 1);
        }

        instituteRepository.saveAll(institutes);
    }

}