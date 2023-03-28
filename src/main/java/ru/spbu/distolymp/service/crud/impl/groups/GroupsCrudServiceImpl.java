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
import ru.spbu.distolymp.entity.lists.ListingProblems;
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
            Group group = groupRepository.findFirstById(id);
            if(group.getListing() != null){
                group.getListing().getProblemList().sort(ListingProblems::compareTo);
            }
            return groupDetailsMapper.toDto(group);
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
    public GroupDetailsDto setListing(Long id, Long listingId) {
        try{
            Listing listing = listingCrudService.getListingByIdOrNull(listingId);
            if(listing != null){
                Group group = groupRepository.findFirstById(id);
                if(group != null){
                    group.setListing(listing);
                    groupRepository.save(group);
                    return groupDetailsMapper.toDto(group);
                }
            }
        }catch (Exception e){
            log.error("An error occurred while setting a list for group", e);
            throw e;
        }
        return null;
    }

    @Override
    @Transactional
    public void removeListingFromAllGroupsByListingId(Long listingId){
        try{
            List<Group> groups = groupRepository.findAllByListingId(listingId);
            for (Group group : groups) {
                group.setListing(null);
            }
            groupRepository.saveAll(groups);
        }catch (Exception e){
            log.error("An error occurred while removing listing from all connected groups", e);
            throw e;
        }
    }

}
