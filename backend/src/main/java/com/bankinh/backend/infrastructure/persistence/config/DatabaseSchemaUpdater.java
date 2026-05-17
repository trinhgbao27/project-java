package com.bankinh.backend.infrastructure.persistence.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseSchemaUpdater {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void ensureSchemaCompatibility() {
        jdbcTemplate.execute("ALTER TABLE donhang ADD COLUMN IF NOT EXISTS ly_do_tu_choi TEXT");
        jdbcTemplate.execute("ALTER TABLE sanpham ADD COLUMN IF NOT EXISTS hinh_anh_a TEXT");
        jdbcTemplate.execute("ALTER TABLE sanpham ADD COLUMN IF NOT EXISTS hinh_anh_b TEXT");
        log.info("Database schema compatibility check completed");
    }
}
