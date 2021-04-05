package ru.spbu.distolymp.service.admin.directories.towns.api;

import org.springframework.ui.ModelMap;

/**
 * @author Vladislav Konovalov
 */
public interface TownService {

    void fillShowAllTownsModelMap(ModelMap modelMap, int numbersOfTownsDisplayed);

}
