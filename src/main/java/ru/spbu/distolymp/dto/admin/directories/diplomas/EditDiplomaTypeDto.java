package ru.spbu.distolymp.dto.admin.directories.diplomas;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.validation.files.annotation.ImageMimeType;
import ru.spbu.distolymp.validation.admin.directories.diplomas.annotation.UniqueDiplomaTypeName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Daria Usova
 */
@Data
@UniqueDiplomaTypeName
public class EditDiplomaTypeDto {

    private Integer id;

    @NotNull(message = "{diploma.name.required}")
    @Size(min = 1, max = 45, message = "{diploma.name.length}")
    private String newName = "";

    @ImageMimeType
    @ToString.Exclude
    private MultipartFile newImage;

    private String oldImage;

    private boolean portraitOrientation;

}