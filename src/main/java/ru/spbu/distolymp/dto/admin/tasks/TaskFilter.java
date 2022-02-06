package ru.spbu.distolymp.dto.admin.tasks;

import lombok.Data;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TaskFilter {
    private int resultSize;
    private String containsTitle;
}
