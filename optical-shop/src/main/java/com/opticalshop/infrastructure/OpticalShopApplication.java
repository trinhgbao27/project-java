package com.opticalshop.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.opticalshop")
@EntityScan(basePackages = "com.opticalshop.adapter.persistence.entity")
public class OpticalShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpticalShopApplication.class, args);
    }
}
