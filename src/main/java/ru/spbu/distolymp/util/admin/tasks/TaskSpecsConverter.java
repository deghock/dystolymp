package ru.spbu.distolymp.util.admin.tasks;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.tasks.TaskFilter;
import ru.spbu.distolymp.entity.tasks.Task;
import static ru.spbu.distolymp.repository.tasks.spec.TaskSpecs.*;

/**
 * @author Vladislav Konovalov
 */
public class TaskSpecsConverter {
    private TaskSpecsConverter() {}

    public static Specification<Task> toSpecs(TaskFilter filter) {
        if (filter == null)
            return null;
        return titleSpec(filter.getContainsTitle());
    }

    private static Specification<Task> titleSpec(String title) {
        if (title == null || title.trim().equals(""))
            return null;
        return containsTitle(title);
    }
}
