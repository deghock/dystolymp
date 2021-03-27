package ru.spbu.distolymp.mapper.entity.geography;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.geography.country.CountryDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Country;

import java.util.List;

/**
 * @author Daria Usova
 */
@Mapper
public interface CountryMapper {

    CountryDto toDto(Country country);

    List<CountryDto> toDtoList(List<Country> countries);

    Country toEntity(CountryDto countryDto);

    default boolean visibleToBoolean(Visible visible) {
        return visible.toBoolean();
    }

    default Visible booleanToVisible(boolean visibleFlag) {
        return Visible.getVisible(visibleFlag);
    }

}
