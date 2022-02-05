package ru.spbu.distolymp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spbu.distolymp.common.files.ImageService;
import ru.spbu.distolymp.common.files.ImageServiceImpl;
import javax.servlet.ServletContext;

/**
 * @author Daria Usova
 */
@Configuration
public class FileConfig {

    @Value("${diplomas.directory}")
    private String diplomaDirectoryPath;

    @Value("${tasks.directory}")
    private String taskDirectoryPath;

    @Bean("diplomaImageService")
    public ImageService diplomaService(ServletContext servletContext) {
        String path = servletContext.getRealPath(diplomaDirectoryPath);
        return new ImageServiceImpl(path);
    }

    @Bean("taskImageService")
    public ImageService taskService(ServletContext servletContext) {
        String path = servletContext.getRealPath(taskDirectoryPath);
        return new ImageServiceImpl(path);
    }
}