package ru.spbu.distolymp.util.admin.directories.regions;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.regions.RegionFilter;
import ru.spbu.distolymp.entity.geography.Region;
import static ru.spbu.distolymp.repository.geography.spec.RegionSpecs.*;

public class RegionSpecsConverter {
    private RegionSpecsConverter() {}

    public static Specification<Region> toSpecs(RegionFilter filter) {
        if (filter == null)
            return null;
        Specification<Region> nameSpec = nameSpec(filter.getContainsName());
        return nameSpec;
    }

    private static Specification<Region> nameSpec(String name) {
        if (name == null || name.trim().equals(""))
            return null;
        return containsName(name);
    }
}
