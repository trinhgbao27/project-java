package com.bankinh.backend.application.mapper;

import com.bankinh.backend.application.dto.request.DonHangRequest;
import com.bankinh.backend.application.dto.response.DonHangResponse;
import com.bankinh.backend.domain.model.DonHang;
import com.bankinh.backend.domain.model.TrangThaiDonHang;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DonHangMapper {

    public DonHangResponse toResponse(DonHang donHang) {
        return new DonHangResponse(
                donHang.getId(),
                donHang.getNguoiDungId(),
                donHang.getTrangThai(),
                donHang.getTongTien(),
                donHang.getTaoLuc(),
                donHang.getCapNhatLuc(),
                null,
                null,
                donHang.getHoTenNguoiNhan(),
                donHang.getSoDienThoai(),
                donHang.getDiaChi(),
                donHang.getLyDoHoanTra(),
                donHang.getTenNganHang(),
                donHang.getSoTaiKhoan(),
                donHang.getTenChuTaiKhoan(),
                donHang.getLyDoTuChoi()
        );
    }

    public DonHang toDomain(DonHangRequest request) {
        DonHang donHang = new DonHang();
        donHang.setNguoiDungId(request.getNguoiDungId());
        donHang.setTrangThai(TrangThaiDonHang.cho_xac_nhan);
        donHang.setTongTien(BigDecimal.ZERO);
        donHang.setHoTenNguoiNhan(request.getHoTenNguoiNhan());
        donHang.setSoDienThoai(request.getSoDienThoai());
        donHang.setDiaChi(request.getDiaChi());
        return donHang;
    }
}