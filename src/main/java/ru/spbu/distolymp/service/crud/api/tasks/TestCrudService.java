package ru.spbu.distolymp.service.crud.api.tasks;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.tests.TestListDto;
import ru.spbu.distolymp.entity.tasks.Test;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public interface TestCrudService {
    List<TestListDto> getTests(Sort sort);
    List<TestListDto> getTests(Sort sort, Specification<Test> spec);
    Optional<Test> getTestById(Long id);
    void saveOrUpdate(Test test, Map<String, byte[]> fileWithNames);
    void deleteTestById(Long id);
}
