package ru.spbu.distolymp.controller.admin.directories.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spbu.distolymp.dto.entity.lists.CategoryDto;
import ru.spbu.distolymp.exception.crud.lists.category.AddNewCategoryException;
import ru.spbu.distolymp.exception.crud.lists.category.UpdateCategoryNameException;
import ru.spbu.distolymp.service.admin.directories.categories.api.CategoryService;

import javax.validation.Valid;

/**
 * @author Daria Usova
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoriesController {

    private final CategoryService categoryService;
    private static final String REDIRECT_CATEGORY_LIST = "redirect:/categories/list";

    @GetMapping("/list")
    public String showAllCategories(ModelMap modelMap,
                                    @SessionAttribute(value = "idDivision", required = false) Long id) {
        if (id == null) {
            return "redirect:/division/entry";
        }

        categoryService.fillShowAllCategoriesModelMap(modelMap, id);
        return "admin/directories/categories/list";
    }

    @PostMapping("/add")
    public String addNewCategory(@Valid CategoryDto categoryDto, BindingResult bindingResult,
                                 RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            return handleAddNewCategoryException(ra);
        }

        categoryService.addNewCategory(categoryDto);
        return REDIRECT_CATEGORY_LIST;
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategoryById(id);
        return REDIRECT_CATEGORY_LIST;
    }

    @PostMapping("/update")
    public String updateCategory(@Valid CategoryDto categoryDto, BindingResult bindingResult,
                                 RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return handleUpdateCategoryNameException(ra);
        }

        categoryService.updateCategoryName(categoryDto);
        return REDIRECT_CATEGORY_LIST;
    }

    @ExceptionHandler(UpdateCategoryNameException.class)
    public String handleUpdateCategoryNameException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Имя категории не было изменено");
        return REDIRECT_CATEGORY_LIST;
    }

    @ExceptionHandler(AddNewCategoryException.class)
    public String handleAddNewCategoryException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Новая категория не была добавлена");
        return REDIRECT_CATEGORY_LIST;
    }

}
