package ru.spbu.distolymp.dto.admin.directories.groups;
import lombok.Data;

@Data
public class ConstraintDto {
    private long id;
    private String startDataTime;
    private String endDataTime;
    private String duration;
}
