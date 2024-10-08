package ru.spbu.distolymp.service.admin.directories.categories.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.entity.division.CategoryDto;

/**
 * @author Daria Usova
 */
public interface CategoryService {

    void fillShowAllCategoriesModelMap(ModelMap modelMap);

    void saveNewCategory(CategoryDto categoryDto);

    void deleteCategoryById(Long id);

    void updateCategoryName(CategoryDto categoryDto);

}
