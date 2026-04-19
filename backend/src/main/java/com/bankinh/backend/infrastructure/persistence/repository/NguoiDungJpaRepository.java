package com.bankinh.backend.infrastructure.persistence.repository;

import com.bankinh.backend.infrastructure.persistence.entity.NguoiDungEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NguoiDungJpaRepository extends JpaRepository<NguoiDungEntity, UUID> {
    boolean existsByEmail(String email);
}