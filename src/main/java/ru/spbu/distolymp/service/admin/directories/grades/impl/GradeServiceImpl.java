package ru.spbu.distolymp.service.admin.directories.grades.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.grades.GradeListDto;
import ru.spbu.distolymp.dto.admin.directories.grades.GradeNameDto;
import ru.spbu.distolymp.dto.admin.directories.listing.ListingNameDto;
import ru.spbu.distolymp.dto.admin.directories.staff.StaffLoginDto;
import ru.spbu.distolymp.dto.entity.lists.GradeEditDto;
import ru.spbu.distolymp.mapper.admin.directories.lists.grades.GradeListMapper;
import ru.spbu.distolymp.mapper.admin.directories.lists.grades.GradeNameMapper;
import ru.spbu.distolymp.mapper.entity.lists.GradeEditMapper;
import ru.spbu.distolymp.repository.lists.GradeRepository;
import ru.spbu.distolymp.service.admin.directories.grades.api.GradeService;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;
import ru.spbu.distolymp.service.crud.api.users.StaffCrudService;
import ru.spbu.distolymp.service.crud.impl.lists.GradeCrudServiceImpl;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Service
public class GradeServiceImpl extends GradeCrudServiceImpl implements GradeService {

    private final StaffCrudService staffCrudService;
    private final ListingCrudService listingCrudService;

    public GradeServiceImpl(GradeListMapper gradeListMapper, GradeNameMapper gradeNameMapper,
                            GradeEditMapper gradeEditMapper, GradeRepository gradeRepository,
                            StaffCrudService staffCrudService, ListingCrudService listingCrudService) {
        super(gradeListMapper, gradeNameMapper, gradeEditMapper, gradeRepository);
        this.staffCrudService = staffCrudService;
        this.listingCrudService = listingCrudService;
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllGradesModelMap(ModelMap modelMap, Long divisionId) {
        GradeNameDto newGrade = new GradeNameDto();
        newGrade.setDivisionId(divisionId);
        List<GradeListDto> grades = getAllGradesByDivisionId(divisionId);
        modelMap.put("grade", newGrade);
        modelMap.put("grades", grades);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditPageModelMap(Long id, Long divisionId, ModelMap modelMap) {
        GradeEditDto gradeDto = getGradeByIdAndDivisionId(id, divisionId);
        List<StaffLoginDto> staffDtoList = staffCrudService.getAllStaffByDivisionId(divisionId);
        List<ListingNameDto> listingDtoList = listingCrudService.getAllListingByDivisionId(divisionId);
        modelMap.put("grade", gradeDto);
        modelMap.put("staffList", staffDtoList);
        modelMap.put("listingList", listingDtoList);
    }

}
