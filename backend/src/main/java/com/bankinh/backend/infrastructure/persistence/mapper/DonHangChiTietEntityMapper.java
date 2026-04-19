package com.bankinh.backend.infrastructure.persistence.mapper;

import com.bankinh.backend.common.exception.ResourceNotFoundException;
import com.bankinh.backend.domain.model.DonHangChiTiet;
import com.bankinh.backend.infrastructure.persistence.entity.DonHangChiTietEntity;
import com.bankinh.backend.infrastructure.persistence.entity.DonHangEntity;
import com.bankinh.backend.infrastructure.persistence.entity.DonKinhEntity;
import com.bankinh.backend.infrastructure.persistence.entity.SanPhamEntity;
import com.bankinh.backend.infrastructure.persistence.repository.DonHangJpaRepository;
import com.bankinh.backend.infrastructure.persistence.repository.DonKinhJpaRepository;
import com.bankinh.backend.infrastructure.persistence.repository.SanPhamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DonHangChiTietEntityMapper {

    private final DonHangJpaRepository donHangJpaRepository;
    private final SanPhamJpaRepository sanPhamJpaRepository;
    private final DonKinhJpaRepository donKinhJpaRepository;

    public DonHangChiTiet toDomain(DonHangChiTietEntity entity) {
        UUID donKinhId = entity.getDonKinh() != null ? entity.getDonKinh().getId() : null;
        return new DonHangChiTiet(
                entity.getId(),
                entity.getDonHang().getId(),
                entity.getSanPham().getId(),
                donKinhId,
                entity.getSoLuong(),
                entity.getGiaBan(),
                entity.getTaoLuc()
        );
    }

    public DonHangChiTietEntity toEntity(DonHangChiTiet domain) {
        DonHangEntity donHangEntity = donHangJpaRepository.findById(domain.getDonHangId())
                .orElseThrow(() -> new ResourceNotFoundException("DonHang not found: " + domain.getDonHangId()));

        SanPhamEntity sanPhamEntity = sanPhamJpaRepository.findById(domain.getSanPhamId())
                .orElseThrow(() -> new ResourceNotFoundException("SanPham not found: " + domain.getSanPhamId()));

        DonKinhEntity donKinhEntity = null;
        if (domain.getDonKinhId() != null) {
            donKinhEntity = donKinhJpaRepository.findById(domain.getDonKinhId())
                    .orElseThrow(() -> new ResourceNotFoundException("DonKinh not found: " + domain.getDonKinhId()));
        }

        DonHangChiTietEntity entity = new DonHangChiTietEntity();
        entity.setId(domain.getId());
        entity.setDonHang(donHangEntity);
        entity.setSanPham(sanPhamEntity);
        entity.setDonKinh(donKinhEntity);
        entity.setSoLuong(domain.getSoLuong());
        entity.setGiaBan(domain.getGiaBan());
        return entity;
    }
}