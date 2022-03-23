package ru.spbu.distolymp.dto.entity.tasks;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.validation.files.annotation.ImageMimeType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TestDto {
    private Long id;

    private Integer status;

    @Size(max = 255)
    private String prefix;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Size(max = 65535)
    private String problemText;

    private String imageFileName;

    @ImageMimeType
    private MultipartFile image;

    private boolean deleteImage;

    @NotNull
    @Range(max = 65535)
    private Integer width;

    @NotNull
    @Range(max = 65535)
    private Integer height;

    private boolean showResult;

    @NotNull
    @Range(max = 65535)
    private Double points;

    @NotNull
    @Range(max = 65535)
    private Double minusPoints;

    @NotNull
    @Range(max = 65535)
    private Double minPoints;

    private String testFolder;

    private String brcFileName;

    private String parFileName;
}
