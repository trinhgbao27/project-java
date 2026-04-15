package com.bankinh.backend.infrastructure.persistence.mapper;

import com.bankinh.backend.common.exception.ResourceNotFoundException;
import com.bankinh.backend.domain.model.DonKinh;
import com.bankinh.backend.infrastructure.persistence.entity.DonKinhEntity;
import com.bankinh.backend.infrastructure.persistence.entity.NguoiDungEntity;
import com.bankinh.backend.infrastructure.persistence.repository.NguoiDungJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DonKinhEntityMapper {

    private final NguoiDungJpaRepository nguoiDungJpaRepository;

    public DonKinh toDomain(DonKinhEntity entity) {
        return new DonKinh(
                entity.getId(),
                entity.getNguoiDung().getId(),
                entity.getOdCau(),
                entity.getOsCau(),
                entity.getKhoangDongTu(),
                entity.getFileUrl(),
                entity.getGhiChu(),
                entity.getTaoLuc(),
                entity.getCapNhatLuc()
        );
    }

    public DonKinhEntity toEntity(DonKinh domain) {
        NguoiDungEntity nguoiDungEntity = nguoiDungJpaRepository.findById(domain.getNguoiDungId())
                .orElseThrow(() -> new ResourceNotFoundException("NguoiDung not found: " + domain.getNguoiDungId()));
        DonKinhEntity entity = new DonKinhEntity();
        entity.setId(domain.getId());
        entity.setNguoiDung(nguoiDungEntity);
        entity.setOdCau(domain.getOdCau());
        entity.setOsCau(domain.getOsCau());
        entity.setKhoangDongTu(domain.getKhoangDongTu());
        entity.setFileUrl(domain.getFileUrl());
        entity.setGhiChu(domain.getGhiChu());
        return entity;
    }
}