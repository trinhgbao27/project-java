package com.bankinh.backend.infrastructure.persistence.mapper;

import com.bankinh.backend.domain.model.SanPham;
import com.bankinh.backend.infrastructure.persistence.entity.SanPhamEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SanPhamEntityMapper {

    public SanPham toDomain(SanPhamEntity entity) {
        return new SanPham(
                entity.getId(),
                entity.getTen(),
                entity.getLoai(),
                entity.getGia(),
                entity.getSoLuongTon(),
                entity.getHinhAnhA(),
                entity.getHinhAnhB(),
                entity.getTaoLuc(),
                entity.getCapNhatLuc()
        );
    }

    public SanPhamEntity toEntity(SanPham domain) {
        SanPhamEntity entity = new SanPhamEntity();
        entity.setId(domain.getId());
        entity.setTen(domain.getTen());
        entity.setLoai(domain.getLoai());
        entity.setGia(domain.getGia());
        entity.setSoLuongTon(domain.getSoLuongTon());
        entity.setHinhAnhA(domain.getHinhAnhA());
        entity.setHinhAnhB(domain.getHinhAnhB());
        return entity;
    }
}