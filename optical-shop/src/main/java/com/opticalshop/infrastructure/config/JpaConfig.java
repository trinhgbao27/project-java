package com.opticalshop.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Khai báo nơi Spring Data JPA quét repository interfaces.
 * @EnableTransactionManagement bật @Transactional trên toàn app.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.opticalshop.adapter.persistence.repository")
public class JpaConfig {
    // Spring Boot tự cấu hình DataSource và EntityManagerFactory
    // qua application.properties — class này chỉ cần khai báo scan path.
}
