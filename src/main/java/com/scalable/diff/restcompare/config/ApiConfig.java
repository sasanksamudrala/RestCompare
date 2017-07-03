package com.scalable.diff.restcompare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Used for configuration of API
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */

@Configuration
public class ApiConfig {
    
    /**
     * Will define how JSON strings in request are de-serialized in POJOs used to Model our data
     * 
     * @return new Jackson Object Mapper
     */
    @Bean
    public ObjectMapper objectMapper () {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new ObjectMapper();
    }
    
    /**
     * Serializes Java objects into a JSON String in the response body
     * 
     * @return new Object Mapper
     */
    @Bean
    public ObjectWriter objectWriter (ObjectMapper objectMapper) {
        return objectMapper.writerWithDefaultPrettyPrinter();
    }

}
