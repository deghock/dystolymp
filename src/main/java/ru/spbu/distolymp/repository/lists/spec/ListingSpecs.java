package ru.spbu.distolymp.repository.lists.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.entity.lists.Listing;

public class ListingSpecs {

    private ListingSpecs() {
    }

    public static Specification<Listing> containsName(String name) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");

    }
}
