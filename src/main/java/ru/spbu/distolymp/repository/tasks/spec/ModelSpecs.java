package ru.spbu.distolymp.repository.tasks.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.entity.tasks.Model;

/**
 * @author Vladislav Konovalov
 */
public class ModelSpecs {
    private ModelSpecs() {}

    public static Specification<Model> containsTitle(String title) {
        return (root, query, builder) ->
                builder.or(builder.like(builder.lower(root.get("title")), "%" + title.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("prefix")), "%" + title.toLowerCase() + "%"));
    }
}
