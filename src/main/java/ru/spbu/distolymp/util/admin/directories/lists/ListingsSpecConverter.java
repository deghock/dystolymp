package ru.spbu.distolymp.util.admin.directories.lists;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.lists.ListingFilter;
import ru.spbu.distolymp.entity.lists.Listing;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.spbu.distolymp.repository.lists.spec.ListingSpecs.containsName;


public class ListingsSpecConverter {

    private ListingsSpecConverter() {
    }

    public static Specification<Listing> toSpecs(ListingFilter listingFilter) {
        if (listingFilter == null) {
            return null;
        }

        Specification<Listing> nameSpec = nameSpec(listingFilter.getContainsName());

        List<Specification<Listing>> specs = new ArrayList<>();
        specs.add(nameSpec);

        return specs.stream()
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(null);
    }

    private static Specification<Listing> nameSpec(String name) {
        if (name == null || name.trim().equals("")) {
            return null;
        }
        return containsName(name);
    }
}
