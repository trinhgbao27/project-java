package com.bankinh.backend.infrastructure.persistence.repository;

import com.bankinh.backend.infrastructure.persistence.entity.DonHangChiTietEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DonHangChiTietJpaRepository extends JpaRepository<DonHangChiTietEntity, UUID> {
    List<DonHangChiTietEntity> findAllByDonHang_Id(UUID donHangId);
}