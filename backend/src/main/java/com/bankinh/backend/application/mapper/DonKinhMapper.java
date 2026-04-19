package com.bankinh.backend.application.mapper;

import com.bankinh.backend.application.dto.request.DonKinhRequest;
import com.bankinh.backend.application.dto.response.DonKinhResponse;
import com.bankinh.backend.domain.model.DonKinh;
import org.springframework.stereotype.Component;

@Component
public class DonKinhMapper {

    public DonKinhResponse toResponse(DonKinh donKinh) {
        return new DonKinhResponse(
                donKinh.getId(),
                donKinh.getNguoiDungId(),
                donKinh.getOdCau(),
                donKinh.getOsCau(),
                donKinh.getKhoangDongTu(),
                donKinh.getFileUrl(),
                donKinh.getGhiChu(),
                donKinh.getTaoLuc(),
                donKinh.getCapNhatLuc()
        );
    }

    public DonKinh toDomain(DonKinhRequest request) {
        DonKinh donKinh = new DonKinh();
        donKinh.setNguoiDungId(request.getNguoiDungId());
        donKinh.setOdCau(request.getOdCau());
        donKinh.setOsCau(request.getOsCau());
        donKinh.setKhoangDongTu(request.getKhoangDongTu());
        donKinh.setFileUrl(request.getFileUrl());
        donKinh.setGhiChu(request.getGhiChu());
        return donKinh;
    }
}