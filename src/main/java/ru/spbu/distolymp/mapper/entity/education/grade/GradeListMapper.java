package ru.spbu.distolymp.mapper.entity.education.grade;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.spbu.distolymp.dto.admin.directories.grades.GradeListDto;
import ru.spbu.distolymp.entity.education.Grade;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;
import ru.spbu.distolymp.service.crud.api.users.StaffCrudService;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Mapper(uses = {StaffCrudService.class, ListingCrudService.class})
public interface GradeListMapper {

    List<GradeListDto> toDtoList(List<Grade> gradeList);

    @Mapping(target = "staffId", source = "staff.id")
    @Mapping(target = "listingId", source = "listing.id")
    @Mapping(target = "staffLogin", source = "staff.login")
    @Mapping(target = "listingName", source = "listing.name")
    GradeListDto toDto(Grade grade);

    @Mapping(target = "staff", source = "staffId")
    @Mapping(target = "listing", source = "listingId")
    Grade toEntity(GradeListDto gradeDto);

}
