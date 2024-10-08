package ru.spbu.distolymp.controller.admin.directories.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spbu.distolymp.dto.entity.division.CategoryDto;
import ru.spbu.distolymp.exception.crud.division.category.AddNewCategoryException;
import ru.spbu.distolymp.exception.crud.division.category.DeleteCategoryException;
import ru.spbu.distolymp.exception.crud.division.category.UpdateCategoryNameException;
import ru.spbu.distolymp.service.admin.directories.categories.api.CategoryService;

import javax.validation.Valid;

/**
 * @author Daria Usova
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private static final String ROOT = "admin/directories/categories/";
    private static final String LIST = ROOT + "list";
    private static final String REDIRECT_LIST = "redirect:/categories/list";
    private static final String ERROR_PARAM = "error";

    @GetMapping("/list")
    public String showAllCategories(ModelMap modelMap) {
        categoryService.fillShowAllCategoriesModelMap(modelMap);
        return LIST;
    }

    @PostMapping("/add")
    public String addNewCategory(@Valid CategoryDto categoryDto, BindingResult bindingResult, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return handleAddNewCategoryException(ra);
        }

        categoryService.saveNewCategory(categoryDto);
        return REDIRECT_LIST;
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategoryById(id);
        return REDIRECT_LIST;
    }

    @PostMapping("/update")
    public String updateCategory(@Valid CategoryDto categoryDto, BindingResult bindingResult, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return handleUpdateCategoryNameException(ra);
        }

        categoryService.updateCategoryName(categoryDto);
        return REDIRECT_LIST;
    }

    @ExceptionHandler(UpdateCategoryNameException.class)
    public String handleUpdateCategoryNameException(RedirectAttributes ra) {
        ra.addFlashAttribute(ERROR_PARAM, "Имя категории не было изменено");
        return REDIRECT_LIST;
    }

    @ExceptionHandler(AddNewCategoryException.class)
    public String handleAddNewCategoryException(RedirectAttributes ra) {
        ra.addFlashAttribute(ERROR_PARAM, "Новая категория не была добавлена");
        return REDIRECT_LIST;
    }

    @ExceptionHandler(DeleteCategoryException.class)
    public String handleDeleteCategoryException(RedirectAttributes ra) {
        ra.addFlashAttribute(ERROR_PARAM, "Категория не была удалена");
        return REDIRECT_LIST;
    }

}
