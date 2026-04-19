package com.bankinh.backend.infrastructure.persistence.repository;

import com.bankinh.backend.infrastructure.persistence.entity.DonHangEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DonHangJpaRepository extends JpaRepository<DonHangEntity, UUID> {
    List<DonHangEntity> findAllByNguoiDung_Id(UUID nguoiDungId);
}