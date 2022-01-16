package ru.spbu.distolymp.repository.tasks.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.entity.tasks.Task;

/**
 * @author Vladislav Konovalov
 */
public class TaskSpecs {
    private TaskSpecs() {}

    public static Specification<Task> containsTitle(String title) {
        return (root, query, builder) ->
                builder.or(builder.like(builder.lower(root.get("title")), "%" + title.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("prefix")), "%" + title.toLowerCase() + "%"));
    }
}
