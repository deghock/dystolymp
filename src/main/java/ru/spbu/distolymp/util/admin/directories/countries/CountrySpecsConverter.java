package ru.spbu.distolymp.util.admin.directories.countries;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryFilter;
import ru.spbu.distolymp.entity.geography.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.spbu.distolymp.repository.geography.spec.CountrySpecs.*;

/**
 * @author Daria Usova
 */
public class CountrySpecsConverter {

    private CountrySpecsConverter() { }

    public static Specification<Country> toSpecs(CountryFilter countryFilter) {
        if (countryFilter == null) {
            return null;
        }

        Specification<Country> nameSpec = nameSpec(countryFilter.getContainsName());
        Specification<Country> visibleSpec = visibleSpec(countryFilter.isShowHidden(), countryFilter.isShowVisible());

        List<Specification<Country>> specs = new ArrayList<>();
        specs.add(nameSpec);
        specs.add(visibleSpec);

        return specs.stream()
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(null);
    }

    private static Specification<Country> nameSpec(String name) {
        if (name == null || name.trim().equals("")) {
            return null;
        }

        return containsName(name);
    }

    private static Specification<Country> visibleSpec(boolean showHidden, boolean showVisible) {
        boolean showAll = showHidden && showVisible;
        boolean showNone = !showHidden && !showVisible;

        if (showAll || showNone) {
            return null;
        }

        if (showHidden) {
            return showHidden();
        }

        return showVisible();
    }

}
