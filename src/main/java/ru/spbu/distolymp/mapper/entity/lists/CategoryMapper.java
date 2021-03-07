package ru.spbu.distolymp.mapper.entity.lists;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.spbu.distolymp.dto.entity.lists.CategoryDto;
import ru.spbu.distolymp.entity.lists.Category;

import java.util.List;

/**
 * @author Daria Usova
 */
@Mapper
public interface CategoryMapper {

    @Mapping(target = "divisionId", source = "division.id")
    CategoryDto toDto(Category category);

    List<CategoryDto> toDtoList(List<Category> categoryList);

    Category toEntity(CategoryDto categoryDto);

}
