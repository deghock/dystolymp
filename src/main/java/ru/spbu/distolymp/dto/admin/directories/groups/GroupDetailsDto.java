package ru.spbu.distolymp.dto.admin.directories.groups;

import lombok.Data;
import ru.spbu.distolymp.dto.entity.lists.listing.ListingDetailsDto;

@Data
public class GroupDetailsDto {
    private Long id;
    private String name;
    private ListingDetailsDto listing;
}
