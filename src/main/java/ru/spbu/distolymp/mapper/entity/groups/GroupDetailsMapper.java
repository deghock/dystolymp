package ru.spbu.distolymp.mapper.entity.groups;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.admin.directories.groups.GroupDetailsDto;
import ru.spbu.distolymp.entity.groups.Group;

@Mapper
public interface GroupDetailsMapper {
    GroupDetailsDto toDto(Group group);
    Group toEntity(GroupDetailsDto groupDetailsDto);
}
