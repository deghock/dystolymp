package ru.spbu.distolymp.dto.admin.directories.lists.grades;

import lombok.Data;

/**
 * @author Vladislav Konovalov
 */
@Data
public class GradeListDto {

    private Long id;

    private String name;

    private String registrationStatus;

    private String codePhrase;

    private String replyTo;

    private String serviceEmail;

    private Long staffId;

    private String staffLogin;

    private Long listingId;

    private String listingName;

}
