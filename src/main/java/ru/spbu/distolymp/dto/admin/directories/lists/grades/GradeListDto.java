package ru.spbu.distolymp.dto.admin.directories.lists.grades;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
public class GradeListDto {

    private Long id;

    @Size(max = 65535)
    @NotNull(message = "{grade.name.required}")
    private String name;

    @NotNull
    private String registrationStatus;

    private String codePhrase;

    private String replyTo;

    private String serviceEmail;

    @NotNull
    private Long divisionId;

    private Long staffId;

    private String staffLogin;

    private Long listingId;

    private String listingName;

}
