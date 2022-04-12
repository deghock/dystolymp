package ru.spbu.distolymp.dto.admin.tests;

import lombok.Data;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TestAnswerDto {
    private Long problemId;
    private Long userId;
    private String testStartDateTime;
    private String ip;
    private String[] questions;
    private String[] userAnswers;
    private String[] types;
}
