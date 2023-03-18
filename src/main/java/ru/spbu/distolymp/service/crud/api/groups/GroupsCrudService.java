package ru.spbu.distolymp.service.crud.api.groups;

import ru.spbu.distolymp.dto.admin.directories.groups.GroupDetailsDto;
import ru.spbu.distolymp.dto.admin.directories.groups.GroupNameDto;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;

import java.util.List;

public interface GroupsCrudService {
    List<GroupNameDto> getAllGroups();
    GroupDetailsDto getSingleGroupById(Long id);
    List<ListingNameDto> getAllListings();
    void setListing(Long id, Long listingId);
    void removeListingFromAllGroupsByListingId(Long listingId);
}
