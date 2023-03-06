package ru.spbu.distolymp.dto.entity.tasks;

import lombok.Data;

@Data
public class ProblemDto {
    private long id;
    private int status;
    private String title;
}
