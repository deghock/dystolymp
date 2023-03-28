package ru.spbu.distolymp.service.admin.directories.groups.api;

import org.springframework.ui.ModelMap;

public interface GroupsService {
    void getGroups(ModelMap modelMap);
    void getSingleGroup(ModelMap modelMap, Long id);
    void setListing(Long id, Long listingId, ModelMap modelMap);
    void getAvailableListings(ModelMap modelMap);
}
