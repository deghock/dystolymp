package ru.spbu.distolymp.mapper.admin.directories.countries;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryDetailsDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Country;

/**
 * @author Daria Usova
 */
@Mapper
public interface CountryDetailsMapper {

    @Mapping(target = "countryId", source = "id")
    CountryDetailsDto toDto(Country country);

    default boolean visibleToBoolean(Visible visible) {
        return visible.toBoolean();
    }

}
