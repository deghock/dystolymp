package ru.spbu.distolymp.validation.files.validator;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.common.files.FileUtils;
import ru.spbu.distolymp.validation.files.annotation.ParamFileMimeType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Vladislav Konovalov
 */
public class ParamFileMimeTypeValidator implements ConstraintValidator<ParamFileMimeType, MultipartFile> {
    @Override
    public void initialize(ParamFileMimeType paramFileMimeType) {
        // Do nothing because ParamFileMimeType annotation has no parameters
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || "".equals(fileName)) return true;
        String fileExtension = FileUtils.getExtensionFromFileName(fileName);
        String fileMimeType = file.getContentType();
        if (!MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE.equals(fileMimeType)) return false;
        return fileExtension.equals(".php");
    }
}
