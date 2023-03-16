package ru.spbu.distolymp.controller.admin.directories.lists;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spbu.distolymp.dto.admin.directories.groups.ConstraintDto;
import ru.spbu.distolymp.dto.admin.directories.lists.ListingFilter;
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

    //TODO: Возможно понадобятся элементы, не до конца понимаю как работает редирект
    private static final String ADD_FROM_LISTING_SCROLL = ROOT + "listing-from-scroll :: #listing";
    private static final String LISTING_PROBLEM_TABLE = ROOT + "listing-problem-table :: #listing";
    private static final String AVAILABLE_PROBLEMS_SCROLL = ROOT + "available-problems-scroll :: #listing";
    private static final String CONSTRAINT_TABLE = ROOT + "constraint-table :: #listing";


    //TODO: Разобраться с вводом данных с фронтэнда

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

    @GetMapping("/filter")
    public String getListings(ListingFilter listingFilter, ModelMap modelMap){
        modelMap.put("listings", listingService.getListingsBy(listingFilter));
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
    public String renameListing(@Valid ListingNameDto listingNameDto) {
        listingService.renameListing(listingNameDto);
        return REDIRECT_LIST;
    }

    @GetMapping("/available_problems")
    public String getAvailableProblems(ModelMap modelMap){
        modelMap.put("available_problems", listingService.getAvailableProblems());
        return AVAILABLE_PROBLEMS_SCROLL;
    }

    @PostMapping("/copy_list")
    public String copyList(Long copyId, String prefix, String newName){
        listingService.copyList(copyId, newName, prefix);
        return REDIRECT_LIST;
    }

    @PostMapping("/add_problems")
    public String addProblems(ModelMap modelMap, List<Long> problemIds, Long id){
        modelMap.put("problems", listingService.addProblems(problemIds, id));
        return LISTING_PROBLEM_TABLE;
    }

    @PostMapping("/set_constraint")
    public String setConstraint(ModelMap modelMap, ConstraintDto constraintDto, Long id){
        modelMap.put("constraint", listingService.setConstraint(id, constraintDto));
        return CONSTRAINT_TABLE;
    }

    @PostMapping("/remove_constraint")
    public String removeConstraint(ModelMap modelMap, Long id){
        listingService.removeConstraint(id);
        modelMap.put("constraint", null);
        return CONSTRAINT_TABLE;
    }

    @PostMapping("/add_problems_from")
    public String addAllFromList(ModelMap modelMap, Long copyId, Long id){
        modelMap.put("problems", listingService.addAllFromList(copyId, id));
        return LISTING_PROBLEM_TABLE;
    }

    @PostMapping("/remove_problem")
    public String removeProblem(ModelMap modelMap, Long id, Long problemId){
        modelMap.put("problems", listingService.removeProblem(id, problemId));
        return LISTING_PROBLEM_TABLE;
    }

    @PostMapping ("/update_order")
    String updateOrder(ModelMap modelMap, Long id, Long problemListingId, Integer direction){
        modelMap.put("problems", listingService.updateOrder(id, problemListingId, direction));
        return  LISTING_PROBLEM_TABLE;
    }
}
