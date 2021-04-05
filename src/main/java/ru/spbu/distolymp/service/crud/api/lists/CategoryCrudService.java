package ru.spbu.distolymp.service.crud.api.lists;

import ru.spbu.distolymp.dto.entity.lists.CategoryDto;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface CategoryCrudService {

    List<CategoryDto> getCategories();

    void saveNewCategory(CategoryDto categoryDto);

    void deleteCategoryById(Long id);

    void updateCategoryName(CategoryDto categoryDto);

}
