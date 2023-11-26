package ru.spbu.distolymp.controller.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spbu.distolymp.security.StaffDetails;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @GetMapping("/info/{id}")
    public String getStaffInfo(@PathVariable("id") long id, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StaffDetails staffDetails = (StaffDetails)auth.getPrincipal();
        model.addAttribute("staffDetails", staffDetails);
        return "admin/staff-info";
    }
}
