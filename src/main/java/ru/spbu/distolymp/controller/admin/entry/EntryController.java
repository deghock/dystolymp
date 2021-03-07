package ru.spbu.distolymp.controller.admin.entry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.spbu.distolymp.service.admin.entry.api.EntryService;

import javax.servlet.http.HttpSession;

/**
 * @author Daria Usova
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/division/entry")
public class EntryController {

    private final EntryService entryService;

    @GetMapping
    public ModelAndView showDivisionPage(HttpSession session) {
        return entryService.entryModelAndView(session);
    }

    @GetMapping("/choose/{id}")
    public String chooseDivision(@PathVariable("id") Long id, HttpSession httpSession) {
        entryService.chooseDivision(id, httpSession);
        return "redirect:/menu/staff";
    }

}
