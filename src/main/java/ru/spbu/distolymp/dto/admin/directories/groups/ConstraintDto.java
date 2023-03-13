package ru.spbu.distolymp.dto.admin.directories.groups;
import lombok.Data;

import java.util.Date;

@Data
public class ConstraintDto {
    private Long id;
    private Date startDataTime;
    private Date endDataTime;
    private Date duration;
}
