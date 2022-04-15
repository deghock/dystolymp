package ru.spbu.distolymp.dto.entity.tasks;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.common.tasks.auxiliary.QuestionDto;
import ru.spbu.distolymp.validation.files.annotation.ImageMimeType;
import ru.spbu.distolymp.validation.files.annotation.ParamFileMimeType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

    private boolean randomOrder;

    private int[] questionsNumber;

    private boolean questionSkip;

    @NotNull
    @Range(max = 65535)
    private Double points;

    @ParamFileMimeType
    private MultipartFile paramFile;

    private String paramFileName;

    @NotNull
    @Range(max = 65535)
    private Double minusPoints;

    private List<QuestionDto> questionList;

    private int[] allQuestionsNumber;

    private String testFolder;

    private String brcFileName;
}
