package ru.spbu.distolymp.service.admin.entry.api;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author Daria Usova
 */
public interface EntryService {

    ModelAndView entryModelAndView(HttpSession session);

    void chooseDivision(Long divisionId, HttpSession session);

}
