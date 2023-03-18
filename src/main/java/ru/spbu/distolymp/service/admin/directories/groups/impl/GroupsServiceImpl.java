package ru.spbu.distolymp.service.admin.directories.groups.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.groups.GroupNameDto;
import ru.spbu.distolymp.mapper.entity.groups.GroupDetailsMapper;
import ru.spbu.distolymp.mapper.entity.groups.GroupNameMapper;
import ru.spbu.distolymp.repository.groups.GroupRepository;
import ru.spbu.distolymp.service.admin.directories.groups.api.GroupsService;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;
import ru.spbu.distolymp.service.crud.impl.groups.GroupsCrudServiceImpl;

@Service
public class GroupsServiceImpl extends GroupsCrudServiceImpl implements GroupsService {
    public GroupsServiceImpl(GroupRepository groupRepository, GroupNameMapper groupNameMapper, GroupDetailsMapper groupDetailsMapper, ListingCrudService listingCrudService) {
        super(groupRepository, groupNameMapper, groupDetailsMapper, listingCrudService);
    }

    @Override
    @Transactional(readOnly = true)
    public void getGroups(ModelMap modelMap) {
        modelMap.put("group", new GroupNameDto());
        modelMap.put("groups", getAllGroups());
    }

    @Override
    @Transactional(readOnly = true)
    public void getSingleGroup(ModelMap modelMap, Long id) {
        modelMap.put("singleGroup", getSingleGroupById(id));
    }

    @Override
    public void setListing(Long id, Long listingId) {
        setListing(id, listingId);
    }

    @Override
    public void getAvailableListings(ModelMap modelMap) {
        modelMap.put("listings", getAllListings());
    }
}
