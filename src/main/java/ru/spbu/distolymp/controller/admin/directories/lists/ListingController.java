package ru.spbu.distolymp.controller.admin.directories.lists;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.spbu.distolymp.dto.admin.directories.lists.ListingFilter;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.service.admin.directories.lists.api.ListingService;

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
}
