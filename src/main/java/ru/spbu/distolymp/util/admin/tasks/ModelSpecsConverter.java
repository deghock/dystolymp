package ru.spbu.distolymp.util.admin.tasks;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.models.ModelFilter;
import ru.spbu.distolymp.entity.tasks.Model;
import ru.spbu.distolymp.repository.tasks.spec.ModelSpecs;

/**
 * @author Vladislav Konovalov
 */
public class ModelSpecsConverter {
    private ModelSpecsConverter() {}

    public static Specification<Model> toSpecs(ModelFilter filter) {
        if (filter == null) return null;
        return titleSpec(filter.getContainsTitle());
    }

    private static Specification<Model> titleSpec(String title) {
        if (title == null || title.trim().equals("")) return null;
        return ModelSpecs.containsTitle(title);
    }
}
