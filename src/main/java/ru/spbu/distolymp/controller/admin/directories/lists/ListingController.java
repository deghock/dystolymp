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
import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;
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

    @GetMapping("/list")
    public String showAllListings(ModelMap modelMap) {
        listingService.fillShowAllListingsModelMap(modelMap);
        return LIST;
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
    public String getAvailableProblems(){
        listingService.getAvailableProblems();
        return "";
    }

    @PostMapping("/add_problems")
    public String addProblems(@Valid List<ProblemDto> problemDtoList, Long id){
        listingService.addProblems(problemDtoList, id);
        return "";
    }

    @PostMapping("/add_problems_from")
    public String addAllFromList(@Valid Long copyId, Long id){
        //listingService.addProblems();
        return "";
    }

    @PostMapping("/copy_list")
    public String copyList(@RequestParam(value = "id") Long id,@RequestParam(value = "copyId") Long copyId, @RequestParam(value = "prefix") String prefix, @RequestParam(value = "newName") String newName){
        listingService.copyList(id, copyId, newName, prefix);
        return "";
    }

    @PostMapping("/set_constraint")
    public String setConstraint(@Valid ConstraintDto constraintDto, Long id){
        listingService.setConstraint(id, constraintDto);
        return "";
    }

    @PostMapping("/remove_constraint")
    public String removeConstraint(@Valid Long id){
        listingService.removeConstraint(id);
        return "";
    }

    @PostMapping("/remove_problem")
    public String removeProblem(@Valid Long id, Long problemId){
        listingService.removeProblem(id, problemId);
        return "";
    }

    @PostMapping ("/update_order")
    String updateOrder(@Valid Long id, Long problemListingId, int direction){
        listingService.updateOrder(id, problemListingId, direction);
        return  "";
    }
}
