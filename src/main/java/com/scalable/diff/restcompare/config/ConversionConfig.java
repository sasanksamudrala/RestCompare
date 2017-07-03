package com.scalable.diff.restcompare.config;

import java.util.Set;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import converter.JsonBinaryDataEntityToSearchResponseConverter;

/**
 * Converter configuration to add all the converters for mapping fields between Entity and POJO class
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */
@Configuration
public class ConversionConfig {
    
    /**
     * Converter set to return all the required converters
     *
     */
    private Set<Converter> getConverters() {
        Set<Converter> converters = new HashSet<>();
        converters.add(new JsonBinaryDataEntityToSearchResponseConverter());
        
        return converters;
    }
    
    
    /**
     * Registers the converters with the Conversion service
     *
     */
    @Bean
    public ConversionService conversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();
        
        return bean.getObject();
    }

}
