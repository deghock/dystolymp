package ru.spbu.distolymp.repository.geography.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Town;

/**
 * @author Vladislav Konovalov
 */
public class TownSpecs {
    private TownSpecs() {}

    public static Specification<Town> containsName(String name) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Town> showHidden() {
        return (root, query, builder) ->
                builder.equal(root.get("visible"), Visible.no);
    }

    public static Specification<Town> showVisible() {
        return (root, query, builder) ->
                builder.equal(root.get("visible"), Visible.yes);
    }

    public static Specification<Town> belongsCountry(String countryName) {
        return (root, query, builder) ->
                builder.equal(root.join("region").join("country").get("name"), countryName);
    }
}
