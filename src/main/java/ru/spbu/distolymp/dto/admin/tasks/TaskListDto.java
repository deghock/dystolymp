package ru.spbu.distolymp.dto.admin.tasks;

import lombok.Data;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TaskListDto {
    private Long id;
    private String prefix;
    private String title;
    private String status;
}
