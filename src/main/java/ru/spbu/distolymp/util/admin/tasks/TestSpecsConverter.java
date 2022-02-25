package ru.spbu.distolymp.util.admin.tasks;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.tests.TestFilter;
import ru.spbu.distolymp.entity.tasks.Test;
import ru.spbu.distolymp.repository.tasks.spec.TestSpecs;

/**
 * @author Vladislav Konovalov
 */
public class TestSpecsConverter {
    private TestSpecsConverter() {}

    public static Specification<Test> toSpecs(TestFilter filter) {
        if (filter == null) return null;
        return titleSpec(filter.getContainsTitle());
    }

    private static Specification<Test> titleSpec(String title) {
        if (title == null || title.trim().equals("")) return null;
        return TestSpecs.containsTitle(title);
    }
}
