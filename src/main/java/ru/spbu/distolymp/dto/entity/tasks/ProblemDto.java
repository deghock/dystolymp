package ru.spbu.distolymp.dto.entity.tasks;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProblemDto {
    private Long id;
    private Integer status;
    @NotNull
    @Size(max = 255)
    private String title;
}
