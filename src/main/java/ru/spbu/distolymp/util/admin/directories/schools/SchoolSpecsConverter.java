package ru.spbu.distolymp.util.admin.directories.schools;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.schools.SchoolFilter;
import ru.spbu.distolymp.entity.education.School;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.spbu.distolymp.repository.education.spec.SchoolSpecs.*;

/**
 * @author Maxim Andreev
 */
public class SchoolSpecsConverter {

    private SchoolSpecsConverter() { }

    public static Specification<School> toSpecs(SchoolFilter schoolFilter) {
        if (schoolFilter == null) {
            return null;
        }

        Specification<School> titleSpec = titleSpec(schoolFilter.getTitle());
        Specification<School> numberSpec = numberSpec(schoolFilter.getNumber());
        Specification<School> countrySpec = countrySpec(schoolFilter.getCountryId());
        Specification<School> regionSpec = regionSpec(schoolFilter.getRegionId());
        Specification<School> townSpec = townSpec(schoolFilter.getTownId());

        List<Specification<School>> specs = new ArrayList<>();
        specs.add(titleSpec);
        specs.add(numberSpec);
        specs.add(countrySpec);
        specs.add(regionSpec);
        specs.add(townSpec);

        return specs.stream()
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(null);
    }

    private static Specification<School> titleSpec(String title) {
        if (title == null || title.trim().equals("")) {
            return null;
        }
        return getSchoolByTitle(title);
    }

    private static Specification<School> numberSpec(Integer number) {
        if (number == null) {
            return null;
        }
        return getSchoolByNumber(number);
    }

    private static Specification<School> countrySpec(Long countryId) {
        if (countryId == null || countryId == -1) {
            return null;
        }
        return getSchoolByCountryId(countryId);
    }

    private static Specification<School> regionSpec(Long regionId) {
        if (regionId == null || regionId == -1) {
            return null;
        }
        return getSchoolsByRegionId(regionId);
    }

    private static Specification<School> townSpec(Long townId) {
        if (townId == null || townId == -1) {
            return null;
        }
        return getSchoolByTownId(townId);
    }
}
