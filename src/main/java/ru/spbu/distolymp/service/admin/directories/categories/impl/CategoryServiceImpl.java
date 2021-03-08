package ru.spbu.distolymp.service.admin.directories.categories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.entity.lists.CategoryDto;
import ru.spbu.distolymp.service.admin.directories.categories.api.CategoryService;
import ru.spbu.distolymp.service.crud.api.lists.CategoryCrudService;

/**
 * @author Daria Usova
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryCrudService categoryCrudService;

    @Override
    public void fillShowAllCategoriesModelMap(ModelMap modelMap, Long divisionId) {
        CategoryDto newCategory = new CategoryDto();
        newCategory.setDivisionId(divisionId);

        modelMap.put("category", newCategory);
        modelMap.put("categories", categoryCrudService.getCategoriesByDivisionId(divisionId));
    }

    @Override
    public void addNewCategory(CategoryDto categoryDto) {
        categoryCrudService.saveNewCategory(categoryDto);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryCrudService.deleteCategoryById(id);
    }

    @Override
    public void updateCategoryName(CategoryDto categoryDto) {
        categoryCrudService.updateCategoryName(categoryDto);
    }

}
