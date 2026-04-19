package com.bankinh.backend.infrastructure.persistence.mapper;

import com.bankinh.backend.common.exception.ResourceNotFoundException;
import com.bankinh.backend.domain.model.DonHang;
import com.bankinh.backend.infrastructure.persistence.entity.DonHangEntity;
import com.bankinh.backend.infrastructure.persistence.entity.NguoiDungEntity;
import com.bankinh.backend.infrastructure.persistence.repository.DonHangJpaRepository;
import com.bankinh.backend.infrastructure.persistence.repository.NguoiDungJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DonHangEntityMapper {

    private final NguoiDungJpaRepository nguoiDungJpaRepository;
    private final DonHangJpaRepository donHangJpaRepository;

    public DonHang toDomain(DonHangEntity entity) {
        return new DonHang(
                entity.getId(),
                entity.getNguoiDung().getId(),
                entity.getTrangThai(),
                entity.getTongTien(),
                entity.getTaoLuc(),
                entity.getCapNhatLuc(),
                entity.getHoTenNguoiNhan(),
                entity.getSoDienThoai(),
                entity.getDiaChi(),
                entity.getLyDoHoanTra(),
                entity.getTenNganHang(),
                entity.getSoTaiKhoan(),
                entity.getTenChuTaiKhoan(),
                entity.getLyDoTuChoi()
        );
    }

    public DonHangEntity toEntity(DonHang domain) {
        NguoiDungEntity nguoiDungEntity = nguoiDungJpaRepository.findById(domain.getNguoiDungId())
                .orElseThrow(() -> new ResourceNotFoundException("NguoiDung not found: " + domain.getNguoiDungId()));

        DonHangEntity entity = domain.getId() != null
                ? donHangJpaRepository.findById(domain.getId()).orElse(new DonHangEntity())
                : new DonHangEntity();

        entity.setNguoiDung(nguoiDungEntity);
        entity.setTrangThai(domain.getTrangThai());
        entity.setTongTien(domain.getTongTien());
        entity.setHoTenNguoiNhan(domain.getHoTenNguoiNhan());
        entity.setSoDienThoai(domain.getSoDienThoai());
        entity.setDiaChi(domain.getDiaChi());
        entity.setLyDoHoanTra(domain.getLyDoHoanTra());
        entity.setTenNganHang(domain.getTenNganHang());
        entity.setSoTaiKhoan(domain.getSoTaiKhoan());
        entity.setTenChuTaiKhoan(domain.getTenChuTaiKhoan());
        entity.setLyDoTuChoi(domain.getLyDoTuChoi());
        return entity;
    }
}