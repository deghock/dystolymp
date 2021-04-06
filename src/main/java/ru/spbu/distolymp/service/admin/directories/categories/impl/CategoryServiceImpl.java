package ru.spbu.distolymp.service.admin.directories.categories.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.entity.division.CategoryDto;
import ru.spbu.distolymp.mapper.entity.division.CategoryMapper;
import ru.spbu.distolymp.repository.division.CategoryRepository;
import ru.spbu.distolymp.service.admin.directories.categories.api.CategoryService;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.impl.division.CategoryCrudServiceImpl;

/**
 * @author Daria Usova
 */
@Service
public class CategoryServiceImpl extends CategoryCrudServiceImpl implements CategoryService {

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
                               DivisionCrudService divisionCrudService) {
        super(categoryRepository, categoryMapper, divisionCrudService);
    }

    @Override
    public void fillShowAllCategoriesModelMap(ModelMap modelMap) {
        modelMap.put("category", new CategoryDto());
        modelMap.put("categories", getCategories());
    }

}
