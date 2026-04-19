package com.bankinh.backend.application.mapper;

import com.bankinh.backend.application.dto.request.DonHangChiTietRequest;
import com.bankinh.backend.application.dto.response.DonHangChiTietResponse;
import com.bankinh.backend.domain.model.DonHangChiTiet;
import org.springframework.stereotype.Component;

@Component
public class DonHangChiTietMapper {
    public DonHangChiTietResponse toResponse(DonHangChiTiet chiTiet) {
    return new DonHangChiTietResponse(
            chiTiet.getId(),
            chiTiet.getDonHangId(),
            chiTiet.getSanPhamId(),
            chiTiet.getDonKinhId(),
            chiTiet.getSoLuong(),
            chiTiet.getGiaBan(),
            chiTiet.getTaoLuc(),
            null  
    );
}

    public DonHangChiTiet toDomain(DonHangChiTietRequest request) {
        DonHangChiTiet chiTiet = new DonHangChiTiet();
        chiTiet.setDonHangId(request.getDonHangId());
        chiTiet.setSanPhamId(request.getSanPhamId());
        chiTiet.setDonKinhId(request.getDonKinhId());
        chiTiet.setSoLuong(request.getSoLuong());
        chiTiet.setGiaBan(request.getGiaBan());
        return chiTiet;
    }
}