package ru.spbu.distolymp.controller.admin.directories;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Daria Usova
 */
@Controller
@RequestMapping("/directories/list")
public class DirectoryController {

    @GetMapping
    public String showDirectoriesPage() {
        return "admin/directories";
    }

}
