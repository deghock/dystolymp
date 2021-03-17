package ru.spbu.distolymp.service.admin.directories.institutes.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.entity.education.InstituteDto;
import ru.spbu.distolymp.entity.education.Institute;
import ru.spbu.distolymp.entity.education.Place;
import ru.spbu.distolymp.exception.admin.directories.institutes.InstituteServiceException;
import ru.spbu.distolymp.mapper.entity.education.InstituteMapper;
import ru.spbu.distolymp.repository.education.InstituteRepository;
import ru.spbu.distolymp.service.admin.directories.institutes.api.InstituteService;
import ru.spbu.distolymp.service.crud.impl.education.InstituteCrudServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log4j
@Service
public class InstituteServiceImpl extends InstituteCrudServiceImpl implements InstituteService {

    public InstituteServiceImpl(InstituteMapper instituteMapper, InstituteRepository instituteRepository) {
        super(instituteMapper, instituteRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllInstitutesModelMap(ModelMap modelMap) {
        List<InstituteDto> institutes = getAllInstitutes();
        List<Long> instituteIdList = new ArrayList<>();

        modelMap.put("institutes", institutes);
        modelMap.put("idList", instituteIdList);
    }

    @Override
    @Transactional
    public void saveOrEdit(InstituteDto instituteDto) {
        saveOrUpdateInstitute(instituteDto);
    }

    @Override
    @Transactional(readOnly = true)
    public InstituteDto getNewInstituteDto() {
        InstituteDto instituteDto = new InstituteDto();

        Integer order = instituteRepository.findMaxOrder() + 1;
        instituteDto.setOrder(order);

        return instituteDto;
    }

    @Override
    @Transactional(readOnly = true)
    public InstituteDto getInstituteDtoById(Long id) {
        return getInstituteById(id);
    }

    @Override
    @Transactional
    public void orderUp(Long id) {
        try {
            Institute institute = instituteRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
            setNewOrder(institute, institute.getOrder() - 1);
        } catch (DataAccessException e) {
            log.error("An error occurred while updating institute order", e);
            throw new InstituteServiceException();
        }
    }

    @Override
    @Transactional
    public void orderDown(Long id) {
        try {
            Institute institute = instituteRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
            setNewOrder(institute, institute.getOrder() + 1);
        } catch (DataAccessException e) {
            log.error("An error occurred while updating institute order", e);
            throw new InstituteServiceException();
        }
    }


    private void setNewOrder(Institute institute, Integer newOrder) {
        Integer order = institute.getOrder();

        Optional<Institute> newOrderInstituteOpt = instituteRepository.findByOrder(newOrder);
        if (!newOrderInstituteOpt.isPresent()) {
            return;
        }

        Institute newOrderInstitute = newOrderInstituteOpt.get();

        newOrderInstitute.setOrder(order);
        institute.setOrder(newOrder);

        instituteRepository.save(newOrderInstitute);
        instituteRepository.save(institute);
    }

    @Override
    @Transactional
    public void deleteInstitutesById(Long[] ids) {
        if (ids.length > 0) {
            deleteInstitutesById(Arrays.asList(ids));
        }
    }

}
