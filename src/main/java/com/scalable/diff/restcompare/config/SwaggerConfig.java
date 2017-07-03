package com.scalable.diff.restcompare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration to integrate Swagger 2 into existing Spring Boot project
 * Add @Profile annotation if not required for other environments
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.scalable.diff.restcompare"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }
    
    private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("RestCompare")
				.description("Default")
				.version("1.0.0")
				.build();
	}

}
