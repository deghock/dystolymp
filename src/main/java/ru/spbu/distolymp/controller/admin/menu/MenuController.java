package ru.spbu.distolymp.controller.admin.menu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Daria Usova
 */
@Controller
@RequestMapping("/menu/staff")
public class MenuController {

    @GetMapping
    public String showMenu() {
        return "admin/staff-menu";
    }

}
