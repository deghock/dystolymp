package ru.spbu.distolymp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spbu.distolymp.security.StaffDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * @author Daria Usova
 */
@Controller
@RequestMapping("/")
public class HomeController {

//    @GetMapping
//    public String home() {
//        return "redirect:/menu/staff";
//    }

//    @GetMapping ("/")
//    public String homePage(ModelMap model) {
//        return "welcome";
//    }

    @GetMapping("/")
    public String start() {return "redirect:/authenticated";}

    @GetMapping ("/welcome")
    public String welcomePage(ModelMap model) {
        return "welcome";
    }

    @GetMapping ("/authenticated")
    public String adminPage(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StaffDetails staffDetails = (StaffDetails)auth.getPrincipal();
        model.addAttribute("staffDetails", staffDetails);
        return "authenticated";
    }

    @GetMapping ("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "welcome";
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

}
