package ru.spbu.distolymp.validation.files.validator;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.validation.files.annotation.ImageMimeType;
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
        boolean noFileWasUploaded = "".equals(multipartFile.getOriginalFilename());
        if (noFileWasUploaded) {
            return true;
        }

        return fileMimeType.equals(MimeTypeUtils.IMAGE_JPEG_VALUE) ||
               fileMimeType.equals(MimeTypeUtils.IMAGE_PNG_VALUE) ||
               fileMimeType.equals(MimeTypeUtils.IMAGE_GIF_VALUE);
    }

}