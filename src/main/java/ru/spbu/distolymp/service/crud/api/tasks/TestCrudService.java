package ru.spbu.distolymp.service.crud.api.tasks;

import org.springframework.data.domain.Sort;
import ru.spbu.distolymp.dto.admin.tests.TestListDto;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface TestCrudService {
    List<TestListDto> getTests(Sort sort);
}
