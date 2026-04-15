package com.bankinh.backend.infrastructure.persistence.mapper;

import com.bankinh.backend.domain.model.NguoiDung;
import com.bankinh.backend.infrastructure.persistence.entity.NguoiDungEntity;
import org.springframework.stereotype.Component;

@Component
public class NguoiDungEntityMapper {

    public NguoiDung toDomain(NguoiDungEntity entity) {
        return new NguoiDung(
                entity.getId(),
                entity.getEmail(),
                entity.getHoTen(),
                entity.getVaiTro(),
                entity.getTaoLuc(),
                entity.getCapNhatLuc()
        );
    }

    public NguoiDungEntity toEntity(NguoiDung domain) {
        NguoiDungEntity entity = new NguoiDungEntity();
        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setHoTen(domain.getHoTen());
        entity.setVaiTro(domain.getVaiTro());
        return entity;
    }
}