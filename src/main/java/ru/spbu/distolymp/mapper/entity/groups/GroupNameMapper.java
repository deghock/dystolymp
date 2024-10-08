package ru.spbu.distolymp.mapper.entity.groups;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.admin.directories.groups.GroupNameDto;
import ru.spbu.distolymp.entity.groups.Group;

import java.util.List;

@Mapper
public interface GroupNameMapper {
    GroupNameDto toDto(Group group);
    Group toEntity(GroupNameDto groupNameDto);
    List<GroupNameDto> toDtoList(List<Group> groupList);
}
