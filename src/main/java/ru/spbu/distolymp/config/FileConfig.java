package ru.spbu.distolymp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spbu.distolymp.common.files.FileService;
import ru.spbu.distolymp.common.files.FileServiceImpl;
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

    @Value("${models.directory}")
    private String modelDirectoryPath;

    @Value("${tests.directory}")
    private String testDirectoryPath;

    @Bean("diplomaFileService")
    public FileService diplomaService(ServletContext servletContext) {
        String path = servletContext.getRealPath(diplomaDirectoryPath);
        return new FileServiceImpl(path);
    }

    @Bean("taskFileService")
    public FileService taskService(ServletContext servletContext) {
        String path = servletContext.getRealPath(taskDirectoryPath);
        return new FileServiceImpl(path);
    }

    @Bean("modelFileService")
    public FileService modelService(ServletContext servletContext) {
        String path = servletContext.getRealPath(modelDirectoryPath);
        return new FileServiceImpl(path);
    }

    @Bean("testFileService")
    public FileService testService(ServletContext servletContext) {
        String path = servletContext.getRealPath(testDirectoryPath);
        return new FileServiceImpl(path);
    }
}