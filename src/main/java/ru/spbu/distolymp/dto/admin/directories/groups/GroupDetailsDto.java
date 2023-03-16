package ru.spbu.distolymp.dto.admin.directories.groups;

import lombok.Data;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingNameDto;

@Data
public class GroupDetailsDto {
    private Long id;
    private String name;
    private ListingNameDto listingNameDto;
}
