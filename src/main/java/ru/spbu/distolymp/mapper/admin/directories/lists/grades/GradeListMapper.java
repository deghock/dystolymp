package ru.spbu.distolymp.mapper.admin.directories.lists.grades;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeListDto;
import ru.spbu.distolymp.entity.lists.Grade;
import ru.spbu.distolymp.service.crud.api.lists.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;
import ru.spbu.distolymp.service.crud.api.users.StaffCrudService;


import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Mapper(uses = {DivisionCrudService.class, StaffCrudService.class, ListingCrudService.class})
public interface GradeListMapper {

    List<GradeListDto> toDtoList(List<Grade> grades);

    @Mapping(target = "divisionId", source = "division.id")
    @Mapping(target = "staffId", source = "staff.id")
    @Mapping(target = "staffLogin", source = "staff.login")
    @Mapping(target = "listingId", source = "listing.id")
    @Mapping(target = "listingName", source = "listing.name")
    GradeListDto toDto(Grade grade);

    @Mapping(target = "division", source = "divisionId")
    @Mapping(target = "staff", source = "staffId")
    @Mapping(target = "listing", source = "listingId")
    Grade toEntity(GradeListDto gradeListDto);

}
