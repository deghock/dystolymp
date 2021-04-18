package ru.spbu.distolymp.service.crud.api.division;

import ru.spbu.distolymp.dto.entity.division.CategoryDto;

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
