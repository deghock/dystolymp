package ru.spbu.distolymp.service.crud.impl.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.common.files.FileService;
import ru.spbu.distolymp.dto.admin.tests.TestListDto;
import ru.spbu.distolymp.entity.tasks.Problem;
import ru.spbu.distolymp.entity.tasks.Test;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.mapper.admin.tests.api.TestListMapper;
import ru.spbu.distolymp.mapper.entity.tasks.api.TestMapper;
import ru.spbu.distolymp.repository.tasks.TestRepository;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import ru.spbu.distolymp.service.crud.api.tasks.TestCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@Primary
@RequiredArgsConstructor
public class TestCrudServiceImpl implements TestCrudService {
    private final TestRepository testRepository;
    private final TestListMapper testListMapper;
    private final ListingProblemCrudService listingProblemCrudService;
    protected final TestMapper testMapper;
    @Autowired
    @Qualifier("testFileService")
    protected FileService fileService;

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

    @Override
    @Transactional
    public void saveOrUpdate(Test test, Map<String, byte[]> fileWithNames) {
        if (test.getId() == null) {
            boolean dirCreated = fileService.createDirectory(test.getTestFolder());
            if (!dirCreated) throw new TechnicalException("Test's directory is not created");
        }
        for (Map.Entry<String, byte[]> fileWithName : fileWithNames.entrySet()) {
            String fileName = fileWithName.getKey();
            byte[] file = fileWithName.getValue();
            if ("".equals(fileName)) continue;
            boolean fileSaved = fileService.saveFile(file, fileName);
            if (!fileSaved) {
                fileService.deleteFiles(fileWithNames.keySet());
                throw new TechnicalException("Test's file with name=" + fileName + " is not saved");
            }
        }
        try {
            testRepository.save(test);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving or updating a test");
            fileService.deleteFiles(fileWithNames.keySet());
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public void deleteTestById(Long id) {
        try {
            Test test = getTestById(id).orElseThrow(EntityNotFoundException::new);
            listingProblemCrudService.deleteByProblemId(id);
            testRepository.delete(test);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while deleting a test with id=" + id, e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public Problem copyFromProblem(Long copyId, Problem problem) {
        try{
            Test copyModel = testRepository.findFirstById(copyId).copyFromProblem(problem);
            testRepository.save(copyModel);
            return copyModel;
        }catch (Exception e){
            log.error(e);
            throw new TechnicalException();
        }
    }
}
