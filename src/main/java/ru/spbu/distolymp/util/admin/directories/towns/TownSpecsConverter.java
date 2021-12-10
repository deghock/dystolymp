package ru.spbu.distolymp.util.admin.directories.towns;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.towns.TownFilter;
import ru.spbu.distolymp.entity.geography.Town;
import static ru.spbu.distolymp.repository.geography.spec.TownSpecs.*;

/**
 * @author Vladislav Konovalov
 */
public class TownSpecsConverter {
    private TownSpecsConverter() {}

    public static Specification<Town> toSpecs(TownFilter filter) {
        if (filter == null)
            return null;
        Specification<Town> nameSpec = nameSpec(filter.getContainsName());
        Specification<Town> visibilitySpec = visibilitySpec(filter.isShowHidden(), filter.isShowVisible());
        Specification<Town> countrySpec = countrySpec(filter.getBelongsCountry());
        if (nameSpec == null && visibilitySpec == null && countrySpec == null)
            return null;
        if (nameSpec == null && visibilitySpec == null)
            return countrySpec;
        if (nameSpec == null && countrySpec == null)
            return visibilitySpec;
        if (visibilitySpec == null && countrySpec == null)
            return nameSpec;
        if (nameSpec == null)
            return visibilitySpec.and(countrySpec);
        if (visibilitySpec == null)
            return nameSpec.and(countrySpec);
        if (countrySpec == null)
            return nameSpec.and(visibilitySpec);
        return nameSpec.and(visibilitySpec.and(countrySpec));
    }

    private static Specification<Town> nameSpec(String name) {
        if (name == null || name.trim().equals(""))
            return null;
        return containsName(name);
    }

    private static Specification<Town> visibilitySpec(boolean showHidden, boolean showVisible) {
        boolean showAll = showHidden && showVisible;
        boolean showNone = !showHidden && !showVisible;
        if (showAll || showNone)
            return null;
        if (showHidden)
            return showHidden();
        return showVisible();
    }

    private static Specification<Town> countrySpec(String countryName) {
        if (countryName == null || countryName.trim().equals(""))
            return null;
        return belongsCountry(countryName);
    }
}
