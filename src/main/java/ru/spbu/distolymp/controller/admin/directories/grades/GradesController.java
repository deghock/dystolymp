package ru.spbu.distolymp.controller.admin.directories.grades;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Vladislav Konovalov
 */
@Controller
@RequestMapping("/classes")
public class GradesController {

    @GetMapping("/list")
    public String showAllClasses() {
        return "admin/directories/grades/list";
    }

}
