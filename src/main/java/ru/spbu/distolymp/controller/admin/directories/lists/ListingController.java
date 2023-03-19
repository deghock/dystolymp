package ru.spbu.distolymp.controller.admin.directories.lists;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;
import ru.spbu.distolymp.dto.admin.directories.lists.ListingFilter;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingDetailsDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.service.admin.directories.lists.api.ListingService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lists")
public class ListingController {

    private final ListingService listingService;
    private static final String ROOT = "admin/directories/lists/";
    private static final String LIST = ROOT + "list";
    private static final String REDIRECT_LIST = "redirect:/lists/list";
    private static final String LISTING_SCROLL = ROOT + "listing-scroll :: #listing";
    private static final String ADD_FROM_LISTING_SCROLL = ROOT + "listing-from-scroll :: #listing-from-scroll";
    private static final String AVAILABLE_PROBLEMS_SCROLL = ROOT + "available-problems-scroll :: #available-problems-scroll";
    //private static final String CONSTRAINT_TABLE = ROOT + "constraint-table :: #constraint-table";
    private static final String SINGLE_LISTING = ROOT + "single-listing :: #single-listing";

    @GetMapping("/list")
    public String showAllListings(ModelMap modelMap) {
        listingService.fillShowAllListingsModelMap(modelMap);
        return LIST;
    }

    @GetMapping("/lists_available")
    public String showAvailableListing(ModelMap modelMap){
        listingService.fillShowAllListingsModelMap(modelMap);
        return ADD_FROM_LISTING_SCROLL;
    }

    @GetMapping("/list/{id}")
    public String getSingleListing(@PathVariable("id") Long id, ModelMap modelMap){
        listingService.getSingleListing(id, modelMap);
        return SINGLE_LISTING;
    }

    @GetMapping("/filter")
    public String getListings(ListingFilter listingFilter, ModelMap modelMap){
        listingService.getListingsBy( modelMap, listingFilter);
        return LISTING_SCROLL;
    }

    @PostMapping("/add")
    public String addNewListing(@Valid ListingNameDto listingNameDto) {
        listingService.saveNewListing(listingNameDto);
        return REDIRECT_LIST;
    }

    @PostMapping("/delete/")
    public String deleteListing(@RequestParam(value = "id") Long id){
        listingService.deleteListing(id);
        return REDIRECT_LIST;
    }

    @PostMapping("/rename")
    public String renameListing(@Valid ListingDetailsDto listingDetailsDto) {
        listingService.renameListing(listingDetailsDto);
        return REDIRECT_LIST;
    }

    @GetMapping("/available_problems")
    public String getAvailableProblems(ModelMap modelMap){
        listingService.getAvailableProblems(modelMap);
        return AVAILABLE_PROBLEMS_SCROLL;
    }

    @PostMapping("/copy_list")
    public String copyList(Long copyId, String prefix, String newName){
        listingService.copyList(copyId, newName, prefix);
        return REDIRECT_LIST;
    }

    @PostMapping("/add_problems")
    public String addProblems(ModelMap modelMap, List<Long> problemIds, Long id){
        listingService.addProblems(problemIds, id, modelMap);
        return SINGLE_LISTING;
    }

    @PostMapping("/set_constraint")
    public String setConstraint(ModelMap modelMap, ConstraintDto constraintDto, Long id){
        listingService.setConstraint(id, constraintDto, modelMap);
        return SINGLE_LISTING;
    }

    @PostMapping("/remove_constraint")
    public String removeConstraint(ModelMap modelMap, Long id){
        listingService.removeConstraint(id, modelMap);
        return SINGLE_LISTING;
    }

    @PostMapping("/add_problems_from")
    public String addAllFromList(ModelMap modelMap, Long copyId, Long id){
        listingService.addAllFromList(copyId, id, modelMap);
        return SINGLE_LISTING;
    }

    @GetMapping("/remove_problem")
    public String removeProblem(ModelMap modelMap, @RequestParam(value = "id")Long id, @RequestParam(value = "problemId") Long problemId){
        listingService.removeProblem(id, problemId, modelMap);
        return SINGLE_LISTING;
    }

    @GetMapping ("/update_order")
    String updateOrder(ModelMap modelMap, Long id, Long problemId, Integer direction){
        listingService.updateOrder(id, problemId, direction, modelMap);
        return SINGLE_LISTING;
    }
}
