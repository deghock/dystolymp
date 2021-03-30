package ru.spbu.distolymp.util.admin.directories.countries;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryFilter;
import ru.spbu.distolymp.entity.geography.Country;

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

        if (nameSpec == null && visibleSpec == null) {
            return null;
        }

        if (nameSpec == null) {
            return visibleSpec;
        }

        if (visibleSpec == null) {
            return nameSpec;
        }

        return nameSpec.and(visibleSpec);
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
