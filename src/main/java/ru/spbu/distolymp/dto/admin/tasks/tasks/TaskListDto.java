package ru.spbu.distolymp.dto.admin.tasks.tasks;

import lombok.Data;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TaskListDto {
    private Long id;
    private String title;
    private String status;
}
