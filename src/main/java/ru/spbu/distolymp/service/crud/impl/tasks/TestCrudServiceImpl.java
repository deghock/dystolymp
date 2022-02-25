package ru.spbu.distolymp.service.crud.impl.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.tests.TestListDto;
import ru.spbu.distolymp.entity.tasks.Test;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.mapper.admin.tests.api.TestListMapper;
import ru.spbu.distolymp.mapper.entity.tasks.TestMapper;
import ru.spbu.distolymp.repository.tasks.TestRepository;
import ru.spbu.distolymp.service.crud.api.tasks.TestCrudService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@RequiredArgsConstructor
public class TestCrudServiceImpl implements TestCrudService {
    private final TestRepository testRepository;
    private final TestListMapper testListMapper;
    protected final TestMapper testMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TestListDto> getTests(Sort sort) {
        try {
            List<Test> testList = testRepository.findByType(2, sort);
            return testListMapper.toDtoList(testList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting tests", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestListDto> getTests(Sort sort, Specification<Test> spec) {
        try {
            List<Test> testList = testRepository.findAll(spec, sort);
            return testListMapper.toDtoList(testList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting tests by specs", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Test> getTestById(Long id) {
        if (id == null) return Optional.empty();
        try {
            return testRepository.findById(id);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting a test with id=" + id, e);
            throw new TechnicalException();
        }
    }
}
