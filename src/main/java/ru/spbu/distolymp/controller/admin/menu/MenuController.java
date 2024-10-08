package ru.spbu.distolymp.controller.admin.menu;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spbu.distolymp.security.StaffDetails;

/**
 * @author Daria Usova
 */
@Controller
@RequestMapping("/menu/staff")
public class MenuController {

    @GetMapping
    public String showMenu(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StaffDetails staffDetails = (StaffDetails)auth.getPrincipal();
        model.addAttribute("staffDetails", staffDetails);
        return "admin/staff-menu";
    }

}
