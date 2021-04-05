package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.lists.CategoryDto;
import ru.spbu.distolymp.entity.lists.Category;
import ru.spbu.distolymp.entity.lists.Division;
import ru.spbu.distolymp.exception.crud.lists.category.AddNewCategoryException;
import ru.spbu.distolymp.exception.crud.lists.category.DeleteCategoryException;
import ru.spbu.distolymp.exception.crud.lists.category.UpdateCategoryNameException;
import ru.spbu.distolymp.mapper.entity.lists.CategoryMapper;
import ru.spbu.distolymp.repository.lists.CategoryRepository;
import ru.spbu.distolymp.service.crud.api.lists.CategoryCrudService;
import ru.spbu.distolymp.service.crud.api.lists.DivisionCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daria Usova
 */
@Log4j
@Service
@RequiredArgsConstructor
public class CategoryCrudServiceImpl implements CategoryCrudService {

    protected final CategoryRepository categoryRepository;
    protected final CategoryMapper categoryMapper;
    private final DivisionCrudService divisionCrudService;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories() {
        try {
            List<Category> categoryList = categoryRepository.findAll();
            return categoryMapper.toDtoList(categoryList);
        } catch(DataAccessException e) {
            log.error("An error occurred while getting categories", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void saveNewCategory(CategoryDto categoryDto) {
        try {
            tryToAddNewCategory(categoryDto);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while adding a new category", e);
            throw new AddNewCategoryException();
        }
    }

    private void tryToAddNewCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        Division division = divisionCrudService.getAnyDivision();

        category.setDivision(division);

        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            categoryRepository.delete(category);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while deleting category with id=" + id, e);
            throw new DeleteCategoryException();
        }
    }

    @Override
    @Transactional
    public void updateCategoryName(CategoryDto categoryDto) {
        try {
            Long id = categoryDto.getId();
            Category category = categoryRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            category.setName(categoryDto.getName());

            categoryRepository.save(category);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while updating category with id=" + categoryDto.getId(), e);
            throw new UpdateCategoryNameException();
        }
    }

}