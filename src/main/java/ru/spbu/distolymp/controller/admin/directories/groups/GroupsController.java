package ru.spbu.distolymp.controller.admin.directories.groups;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.spbu.distolymp.service.admin.directories.groups.api.GroupsService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupsController {
    private final GroupsService groupsService;
    private static final String ROOT = "admin/directories/groups/";
    private static final String LIST = ROOT + "list";
    private static final String SINGLE_GROUP = ROOT + "group";
    private static final String REDIRECT = "redirect:/groups/list";
    private static final String REDIRECT_SINGLE_GROUP = "redirect:/groups/group/";
    private static final String LISTING_SCROLL = ROOT + "listing-scroll :: #listing";

    @GetMapping("/list")
    public String getGroups(ModelMap modelMap){
        groupsService.getGroups(modelMap);
        return LIST;
    }

    @GetMapping("/group/{id}")
    public String getSingleGroup(@PathVariable("id") Long id, ModelMap modelMap){
        groupsService.getSingleGroup(modelMap, id);
        return SINGLE_GROUP;
    }

    @GetMapping("/group/set_listing")
    public String setListing(@RequestParam(value = "id") Long id, @RequestParam(value = "listingId") Long listingId, ModelMap modelMap){
        groupsService.setListing(id, listingId, modelMap);
        return REDIRECT_SINGLE_GROUP + id;
    }

    //TODO: Заменить метод на метод из ListingController
    @GetMapping("/group/available_listings")
    public String getAvailableListing(ModelMap modelMap){
        groupsService.getAvailableListings(modelMap);
        return LISTING_SCROLL;
    }
}
