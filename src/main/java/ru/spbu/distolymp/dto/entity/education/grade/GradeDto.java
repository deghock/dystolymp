package ru.spbu.distolymp.dto.entity.education.grade;

import lombok.Data;
import ru.spbu.distolymp.validation.common.annotation.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
public class GradeDto {

    private Long id;

    @NotNull(message = "{grade.name.null}")
    @Size(max = 50, message = "{grade.name.tooLong}")
    private String name;

    @NotNull
    private String registrationStatus;

    @Size(max = 255, message = "{grade.codePhrase.tooLong}")
    private String codePhrase;

    @Size(max = 255, message = "{grade.email.tooLong}")
    @Email
    private String replyTo;

    @Size(max = 255, message = "{grade.replyName.tooLong}")
    private String replyName;

    @Size(max = 255, message = "{grade.email.tooLong}")
    @Email
    private String serviceEmail;

    @Size(max = 65535, message = "{grade.text.tooLong}")
    private String beforeText;

    @Size(max = 65535, message = "{grade.text.tooLong}")
    private String afterText;

    private Long staffId;

    private Long listingId;

}
