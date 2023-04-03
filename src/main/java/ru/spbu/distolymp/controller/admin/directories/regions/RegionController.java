package ru.spbu.distolymp.controller.admin.directories.regions;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spbu.distolymp.dto.admin.directories.regions.RegionDetailsDto;
import ru.spbu.distolymp.dto.admin.directories.regions.RegionFilter;
import ru.spbu.distolymp.dto.admin.directories.towns.TownFilter;
import ru.spbu.distolymp.service.admin.directories.regions.api.RegionService;

@Log4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/regions")
public class RegionController {

    private final RegionService regionService;
    private static final int DEFAULT_RESULT_SIZE = 20;
    private static final String ROOT = "admin/directories/regions/";
    private static final String LIST_PAGE = ROOT + "list";
    private static final String DETAILS_PAGE = ROOT + "details";
    private static final String EDIT_PAGE = ROOT + "edit";
    private static final String REDIRECT_LIST = "redirect:/regions/list";
    private static final String REGIONS_TABLE = ROOT + "regions-table :: #regions-table";

    @GetMapping("/list")
    public String getRegions(ModelMap modelMap) {
        regionService.fillShowAllRegionsModelMap(modelMap, DEFAULT_RESULT_SIZE);
        return LIST_PAGE;
    }

    @GetMapping("/details/{id}")
    public String getRegionDetails(ModelMap modelMap, @PathVariable("id") Long regionId) {
        regionService.fillShowRegionDetailsModelMap(modelMap, regionId);
        return DETAILS_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(ModelMap modelMap, @PathVariable("id") Long townId) {
        regionService.fillShowEditPageModelMap(modelMap, townId);
        return EDIT_PAGE;
    }

    @GetMapping("/filter")
    public String getRegionsByFilter(RegionFilter regionFilter, ModelMap modelMap) {
        regionService.fillShowRegionTableByFilterModelMap(regionFilter, modelMap);
        return REGIONS_TABLE;
    }

    @GetMapping("/add")
    public String getAddPage(ModelMap modelMap) {
        regionService.fillShowEditPageModelMap(modelMap);
        return EDIT_PAGE;
    }

    @PostMapping("/save-or-edit")
    public String saveOrUpdate(@ModelAttribute("region") RegionDetailsDto regionDetailsDto, ModelMap modelMap) {
        regionService.saveOrUpdate(regionDetailsDto);
        return REDIRECT_LIST;
    }

    @PostMapping("/delete")
    public String deleteSelectedRegions(@RequestParam("ids") Long[] ids) {
        regionService.deleteRegionsByIds(ids);
        return REDIRECT_LIST;
    }
}
