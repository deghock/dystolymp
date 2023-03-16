package ru.spbu.distolymp.validation.admin.directories.diplomas.validator;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.validation.admin.directories.diplomas.annotation.MultipartFileUploaded;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Daria Usova
 */
public class MultipartFileUploadedValidator implements ConstraintValidator<MultipartFileUploaded, MultipartFile> {

    @Override
    public void initialize(MultipartFileUploaded constraintAnnotation) {
        // Do nothing because MultipartFileUploaded annotation has no parameters
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (multipartFile == null || multipartFile.getContentType() == null) {
            return true;
        }

        String fileMimeType = multipartFile.getContentType();
        return !fileMimeType.equals(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE);
    }

}