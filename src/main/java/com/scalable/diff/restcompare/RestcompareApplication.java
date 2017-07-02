package com.scalable.diff.restcompare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring boot application class
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 * 
 */

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class RestcompareApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestcompareApplication.class, args);
	}
}
