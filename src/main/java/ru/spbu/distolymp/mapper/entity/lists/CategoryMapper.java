package ru.spbu.distolymp.mapper.entity.lists;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.lists.CategoryDto;
import ru.spbu.distolymp.entity.lists.Category;

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
