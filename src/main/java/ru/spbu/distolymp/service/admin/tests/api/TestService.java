package ru.spbu.distolymp.service.admin.tests.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.tests.TestFilter;

/**
 * @author Vladislav Konovalov
 */
public interface TestService {
    void fillShowAllTestModelMap(ModelMap modelMap);
    void fillShowAllTestModelMap(ModelMap modelMap, TestFilter testFilter);
}
