package ru.spbu.distolymp.service.crud.impl.groups;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.exception.DataException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.groups.GroupDetailsDto;
import ru.spbu.distolymp.dto.admin.directories.groups.GroupNameDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;
import ru.spbu.distolymp.entity.groups.Group;
import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.mapper.entity.groups.GroupDetailsMapper;
import ru.spbu.distolymp.mapper.entity.groups.GroupNameMapper;
import ru.spbu.distolymp.repository.groups.GroupRepository;
import ru.spbu.distolymp.service.crud.api.groups.GroupsCrudService;
import ru.spbu.distolymp.service.crud.api.lists.ListingCrudService;

import java.util.List;

@Log4j
@Service
@Primary
@RequiredArgsConstructor
public class GroupsCrudServiceImpl implements GroupsCrudService{
    private final GroupRepository groupRepository;
    private final GroupNameMapper groupNameMapper;
    private final GroupDetailsMapper groupDetailsMapper;
    private final ListingCrudService listingCrudService;

    @Override
    @Transactional(readOnly = true)
    public List<GroupNameDto> getAllGroups() {
        try{
            return groupNameMapper.toDtoList(groupRepository.findAll());
        }catch (DataException e){
            log.error("An error occurred while getting all groups", e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GroupDetailsDto getSingleGroupById(Long id) {
        try {
            return groupDetailsMapper.toDto(groupRepository.findFirstById(id));
        }catch (DataException e){
            log.error("An error occurred while getting group", e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListingNameDto> getAllListings() {
        try{
            return listingCrudService.getAllListings();
        }catch (Exception e){
            log.error("An error occurred while getting all available listings", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void setListing(Long id, Long listingId) {
        try{
            Listing listing = listingCrudService.getListingByIdOrNull(listingId);
            Group group = groupRepository.findFirstById(id);
            group.setListing(listing);
            groupRepository.save(group);
        }catch (DataException e){
            log.error("An error occurred while setting a list for group", e);
        }
    }

    @Override
    @Transactional()
    public void removeListingFromAllGroupsByListingId(Long listingId){
        try{
            List<Group> groups = groupRepository.findAllByListingId(listingId);
            for (Group group : groups) {
                group.setListing(null);
            }
            groupRepository.saveAll(groups);
        }catch (Exception e){
            log.error("An error occurred while removing listing from all connected groups");
            throw e;
        }
    }

}
