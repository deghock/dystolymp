package ru.spbu.distolymp.dto.admin.directories.diplomas;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.validation.files.annotation.ImageMimeType;
import ru.spbu.distolymp.validation.files.annotation.MultipartFileUploaded;
import ru.spbu.distolymp.validation.admin.directories.diplomas.annotation.UniqueDiplomaTypeName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Daria Usova
 */
@Data
public class NewDiplomaTypeDto {

    @UniqueDiplomaTypeName
    @NotNull(message = "{diploma.name.required}")
    @Size(min = 1, max = 45, message = "{diploma.name.length}")
    private String name;

    @ImageMimeType
    @ToString.Exclude
    @MultipartFileUploaded
    private MultipartFile image;

    private boolean portraitOrientation;

}