package ru.spbu.distolymp.mapper.entity.education.grade;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.spbu.distolymp.dto.entity.education.grade.GradeDto;
import ru.spbu.distolymp.entity.education.Grade;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;
import ru.spbu.distolymp.service.crud.api.users.StaffCrudService;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Mapper(uses = {StaffCrudService.class, ListingCrudService.class})
public interface GradeMapper {

    List<GradeDto> toDtoList(List<Grade> gradeList);

    @Mapping(target = "staffId", source = "staff.id")
    @Mapping(target = "listingId", source = "listing.id")
    GradeDto toDto(Grade grade);

    @Mapping(target = "staff", source = "staffId")
    @Mapping(target = "listing", source = "listingId")
    Grade toEntity(GradeDto gradeDto);

}
