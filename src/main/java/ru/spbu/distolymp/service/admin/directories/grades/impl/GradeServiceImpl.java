package ru.spbu.distolymp.service.admin.directories.grades.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.grades.GradeListDto;
import ru.spbu.distolymp.dto.entity.education.grade.GradeNameDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.dto.entity.users.staff.StaffLoginDto;
import ru.spbu.distolymp.dto.entity.education.grade.GradeDto;
import ru.spbu.distolymp.mapper.admin.directories.lists.grades.GradeListMapper;
import ru.spbu.distolymp.mapper.entity.education.grade.GradeNameMapper;
import ru.spbu.distolymp.mapper.entity.education.grade.GradeMapper;
import ru.spbu.distolymp.repository.education.GradeRepository;
import ru.spbu.distolymp.service.admin.directories.grades.api.GradeService;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;
import ru.spbu.distolymp.service.crud.api.users.StaffCrudService;
import ru.spbu.distolymp.service.crud.impl.education.GradeCrudServiceImpl;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Service
public class GradeServiceImpl extends GradeCrudServiceImpl implements GradeService {

    private final StaffCrudService staffCrudService;
    private final ListingCrudService listingCrudService;

    public GradeServiceImpl(GradeListMapper gradeListMapper, GradeNameMapper gradeNameMapper,
                            GradeMapper gradeMapper, GradeRepository gradeRepository,
                            DivisionCrudService divisionCrudService, StaffCrudService staffCrudService,
                            ListingCrudService listingCrudService) {
        super(gradeListMapper, gradeNameMapper, gradeMapper, gradeRepository, divisionCrudService);
        this.staffCrudService = staffCrudService;
        this.listingCrudService = listingCrudService;
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllGradesModelMap(ModelMap modelMap) {
        GradeNameDto newGrade = new GradeNameDto();
        List<GradeListDto> grades = getAllGrades();

        modelMap.put("grade", newGrade);
        modelMap.put("grades", grades);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditPageModelMap(Long id, Long divisionId, ModelMap modelMap) {
        GradeDto gradeDto = getGradeByIdAndDivisionId(id, divisionId);
        List<StaffLoginDto> staffDtoList = staffCrudService.getAllStaffByDivisionId(divisionId);
        List<ListingNameDto> listingDtoList = listingCrudService.getAllListingByDivisionId(divisionId);

        modelMap.put("grade", gradeDto);
        modelMap.put("staffList", staffDtoList);
        modelMap.put("listingList", listingDtoList);
    }

}
