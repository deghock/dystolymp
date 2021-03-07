package ru.spbu.distolymp.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class JsonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .build();
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter messageConverter() {
        return new MappingJackson2HttpMessageConverter(objectMapper());
    }

}
