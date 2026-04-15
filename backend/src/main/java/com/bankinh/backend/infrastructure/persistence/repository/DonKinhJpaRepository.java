package com.bankinh.backend.infrastructure.persistence.repository;

import com.bankinh.backend.infrastructure.persistence.entity.DonKinhEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DonKinhJpaRepository extends JpaRepository<DonKinhEntity, UUID> {
    List<DonKinhEntity> findAllByNguoiDung_Id(UUID nguoiDungId);
}