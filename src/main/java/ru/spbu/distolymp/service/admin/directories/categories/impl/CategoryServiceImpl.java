package ru.spbu.distolymp.service.admin.directories.categories.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.entity.lists.CategoryDto;
import ru.spbu.distolymp.mapper.entity.lists.CategoryMapper;
import ru.spbu.distolymp.repository.lists.CategoryRepository;
import ru.spbu.distolymp.service.admin.directories.categories.api.CategoryService;
import ru.spbu.distolymp.service.crud.api.lists.DivisionCrudService;
import ru.spbu.distolymp.service.crud.impl.lists.CategoryCrudServiceImpl;

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
