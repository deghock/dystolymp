package ru.spbu.distolymp.validation.admin.directories.diplomas.validator;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.validation.admin.directories.diplomas.annotation.ImageMimeType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Daria Usova
 */
public class ImageMimeTypeValidator implements ConstraintValidator<ImageMimeType, MultipartFile> {

    @Override
    public void initialize(ImageMimeType constraintAnnotation) {
        // Do nothing because ImageMimeType annotation has no parameters
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (multipartFile == null || multipartFile.getContentType() == null) {
            return true;
        }

        String fileMimeType = multipartFile.getContentType();
        boolean noFileWasUploaded = fileMimeType.equals(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE);
        if (noFileWasUploaded) {
            return true;
        }

        return fileMimeType.equals(MimeTypeUtils.IMAGE_JPEG_VALUE) ||
               fileMimeType.equals(MimeTypeUtils.IMAGE_PNG_VALUE);
    }

}