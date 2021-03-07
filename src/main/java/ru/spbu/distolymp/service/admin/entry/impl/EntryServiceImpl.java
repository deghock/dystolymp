package ru.spbu.distolymp.service.admin.entry.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import ru.spbu.distolymp.dto.entity.lists.DivisionDto;
import ru.spbu.distolymp.service.crud.api.lists.DivisionCrudService;
import ru.spbu.distolymp.service.admin.entry.api.EntryService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Daria Usova
 */
@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {

    private final DivisionCrudService divisionCrudService;
    private final String DIVISION_ATTRIBUTE = "idDivision";

    @Override
    public ModelAndView entryModelAndView(HttpSession session) {
        List<DivisionDto> divisionDtoList = divisionCrudService.getDivisionList();

        if (divisionDtoList.size() == 1) {
            session.setAttribute(DIVISION_ATTRIBUTE, divisionDtoList.get(0).getId());
            return new ModelAndView("redirect:/menu/staff");
        }

        ModelAndView entryModelAndView = new ModelAndView("admin/division-entry");
        entryModelAndView.addObject("divisions", divisionDtoList);

        return entryModelAndView;
    }

    @Override
    public void chooseDivision(Long divisionId, HttpSession session) {
        session.setAttribute(DIVISION_ATTRIBUTE, divisionId);
    }

}
