package ru.spbu.distolymp.validation.files.validator;

import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
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
        if (multipartFile == null || multipartFile.getContentType() == null) return true;
        String fileMimeType = multipartFile.getContentType();
        boolean noFileWasUploaded = fileMimeType.equals(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE);
        if (noFileWasUploaded) return true;
        return fileMimeType.equals(new MimeType("brc").getType());
    }
}
