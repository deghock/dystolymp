package ru.spbu.distolymp.repository.education.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.entity.education.School;

/**
 * @author Maxim Andreev
 */
public class SchoolSpecs {
    private SchoolSpecs() {}

    public static Specification<School> getSchoolByTitle(String title) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<School> getSchoolByNumber(Integer number) {
        return (root, query, builder) ->
                builder.equal(root.get("number"), number);
    }

    public static Specification<School> getSchoolByCountryId(Long countryId) {
        return (root, query, builder) ->
                builder.equal(root.get("town").<Long> get("region").<Long> get("country").<Long> get("id"), countryId);
    }


    public static Specification<School> getSchoolsByRegionId(Long regionId) {
        return (root, query, builder) ->
                builder.equal(root.get("town").<Long> get("region").<Long> get("id"), regionId);
    }

    public static Specification<School> getSchoolByTownId(Long townId) {
        return (root, query, builder) ->
                builder.equal(root.get("town").<Long> get("id"), townId);
    }

    /*public static Specification<School> containsDistrictId(Long districtId) {
        return (root, query, builder) ->
                builder.equal(root.get("district"), districtId);
    }*/

}
