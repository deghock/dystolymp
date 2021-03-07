package ru.spbu.distolymp.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Daria Usova
 */
@Controller
@RequestMapping("/")
public class DemoController {

    @GetMapping
    public String home() {
        return "home";
    }

}
