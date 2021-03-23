package ru.spbu.distolymp.mapper.entity.lists;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.spbu.distolymp.dto.entity.lists.GradeEditDto;
import ru.spbu.distolymp.entity.lists.Grade;
import ru.spbu.distolymp.service.crud.api.lists.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;
import ru.spbu.distolymp.service.crud.api.users.StaffCrudService;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Mapper(uses = {DivisionCrudService.class, StaffCrudService.class, ListingCrudService.class})
public interface GradeEditMapper {

    List<GradeEditDto> toDtoList(List<Grade> gradeList);

    @Mapping(target = "divisionId", source = "division.id")
    @Mapping(target = "staffId", source = "staff.id")
    @Mapping(target = "listingId", source = "listing.id")
    GradeEditDto toDto(Grade grade);

    @Mapping(target = "division", source = "divisionId")
    @Mapping(target = "staff", source = "staffId")
    @Mapping(target = "listing", source = "listingId")
    Grade toEntity(GradeEditDto gradeEditDto);

}
