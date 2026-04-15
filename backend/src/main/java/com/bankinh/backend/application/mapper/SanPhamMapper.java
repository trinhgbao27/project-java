package com.bankinh.backend.application.mapper;

import com.bankinh.backend.application.dto.request.SanPhamRequest;
import com.bankinh.backend.application.dto.response.SanPhamResponse;
import com.bankinh.backend.domain.model.SanPham;
import org.springframework.stereotype.Component;

@Component
public class SanPhamMapper {

    public SanPhamResponse toResponse(SanPham sanPham) {
        return new SanPhamResponse(
                sanPham.getId(),
                sanPham.getTen(),
                sanPham.getLoai(),
                sanPham.getGia(),
                sanPham.getSoLuongTon(),
                sanPham.getHinhAnhA(),
                sanPham.getHinhAnhB(),
                sanPham.getTaoLuc(),
                sanPham.getCapNhatLuc()
        );
    }

    public SanPham toDomain(SanPhamRequest request) {
        SanPham sanPham = new SanPham();
        sanPham.setTen(request.getTen());
        sanPham.setLoai(request.getLoai());
        sanPham.setGia(request.getGia());
        sanPham.setSoLuongTon(request.getSoLuongTon());
        sanPham.setHinhAnhA(request.getHinhAnhA());
        sanPham.setHinhAnhB(request.getHinhAnhB());
        return sanPham;
    }
}