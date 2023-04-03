package ru.spbu.distolymp.repository.geography.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.entity.geography.Region;

public class RegionSpecs {
    private RegionSpecs() {}

    public static Specification<Region> containsName(String name) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
