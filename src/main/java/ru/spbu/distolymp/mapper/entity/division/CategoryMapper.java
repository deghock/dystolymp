package ru.spbu.distolymp.mapper.entity.division;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.division.CategoryDto;
import ru.spbu.distolymp.entity.division.Category;

import java.util.List;

/**
 * @author Daria Usova
 */
@Mapper
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);

    List<CategoryDto> toDtoList(List<Category> categoryList);

}
