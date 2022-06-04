package ru.spbu.distolymp.repository.geography.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Country;

/**
 * @author Daria Usova
 */
public class CountrySpecs {

    private CountrySpecs() {}

    public static Specification<Country> containsName(String name) {
        return (root, query, builder) ->
            builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Country> showVisible() {
        return (root, query, builder) ->
                builder.equal(root.get("visible"), Visible.yes);
    }

    public static Specification<Country> showHidden() {
        return (root, query, builder) ->
                builder.equal(root.get("visible"), Visible.no);
    }

}
