package com.bankinh.backend.infrastructure.persistence.repository;

import com.bankinh.backend.infrastructure.persistence.entity.SanPhamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SanPhamJpaRepository extends JpaRepository<SanPhamEntity, UUID> {
}