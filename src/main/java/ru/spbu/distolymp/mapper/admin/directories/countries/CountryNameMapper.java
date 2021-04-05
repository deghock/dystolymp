package ru.spbu.distolymp.mapper.admin.directories.countries;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;
import ru.spbu.distolymp.entity.geography.Country;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface CountryNameMapper {

    CountryNameDto toDto(Country country);

    Country toEntity(CountryNameDto countryNameDto);

    List<CountryNameDto> toDtoList(List<Country> countryList);

}
