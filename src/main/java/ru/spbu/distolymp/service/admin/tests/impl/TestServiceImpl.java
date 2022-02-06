package ru.spbu.distolymp.service.admin.tests.impl;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.tests.TestFilter;
import ru.spbu.distolymp.dto.admin.tests.TestListDto;
import ru.spbu.distolymp.entity.tasks.Test;
import ru.spbu.distolymp.mapper.admin.tests.api.TestListMapper;
import ru.spbu.distolymp.repository.tasks.TestRepository;
import ru.spbu.distolymp.service.admin.tests.api.TestService;
import ru.spbu.distolymp.service.crud.impl.tasks.TestCrudServiceImpl;
import ru.spbu.distolymp.util.admin.tasks.TestSpecsConverter;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Service
public class TestServiceImpl extends TestCrudServiceImpl implements TestService {
    private static final Sort SORT_BY_ID_DESC = Sort.by("id").descending();
    private static final String TESTS_PARAM = "testList";

    public TestServiceImpl(TestRepository testRepository,
                           TestListMapper testListMapper) {
        super(testRepository, testListMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllTestModelMap(ModelMap modelMap) {
        List<TestListDto> testDtoList = getTests(SORT_BY_ID_DESC);
        modelMap.put(TESTS_PARAM, testDtoList);
        modelMap.put("testForCopy", new TestListDto());
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllTestModelMap(ModelMap modelMap, TestFilter testFilter) {
        Specification<Test> specs = TestSpecsConverter.toSpecs(testFilter);
        List<TestListDto> testDtoList;
        if (specs == null)
            testDtoList = getTests(SORT_BY_ID_DESC);
        else
            testDtoList = getTests(SORT_BY_ID_DESC, specs);
        modelMap.put(TESTS_PARAM, testDtoList);
    }
}
