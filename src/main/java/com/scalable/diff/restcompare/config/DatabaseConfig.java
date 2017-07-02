package com.scalable.diff.restcompare.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Database configuration
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */

@EnableJpaRepositories("com.scalable.diff.restcompare.repository")
@EnableTransactionManagement
public class DatabaseConfig {

}
