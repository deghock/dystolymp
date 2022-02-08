package ru.spbu.distolymp.validation.files.validator;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.common.files.FilesUtils;
import ru.spbu.distolymp.validation.files.annotation.BarsicFileMimeType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Vladislav Konovalov
 */
public class BarsicFileMimeTypeValidator implements ConstraintValidator<BarsicFileMimeType, MultipartFile> {
    @Override
    public void initialize(BarsicFileMimeType barsicFileMimeType) {
        // Do nothing because BarsicFileMimeType annotation has no parameters
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if ("".equals(multipartFile.getOriginalFilename())) return true;
        String fileName = multipartFile.getOriginalFilename();
        if (fileName == null) return true;
        String fileExtension = FilesUtils.getExtensionFromFileName(fileName);
        String fileMimeType = multipartFile.getContentType();
        if (!MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE.equals(fileMimeType)) return false;
        return fileExtension.equals(".brc");
    }
}
