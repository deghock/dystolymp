package ru.spbu.distolymp.service.crud.impl.division;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.division.CategoryDto;
import ru.spbu.distolymp.entity.division.Category;
import ru.spbu.distolymp.entity.division.Division;
import ru.spbu.distolymp.exception.crud.division.category.AddNewCategoryException;
import ru.spbu.distolymp.exception.crud.division.category.DeleteCategoryException;
import ru.spbu.distolymp.exception.crud.division.category.UpdateCategoryNameException;
import ru.spbu.distolymp.mapper.entity.division.CategoryMapper;
import ru.spbu.distolymp.repository.division.CategoryRepository;
import ru.spbu.distolymp.service.crud.api.division.CategoryCrudService;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;

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