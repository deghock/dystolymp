package ru.spbu.distolymp.repository.tasks.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.entity.tasks.Test;

/**
 * @author Vladislav Konovalov
 */
public class TestSpecs {
    private TestSpecs() {}

    public static Specification<Test> containsTitle(String title) {
        return (root, query, builder) ->
                builder.or(builder.like(builder.lower(root.get("title")), "%" + title.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("prefix")), "%" + title.toLowerCase() + "%"));
    }
}
