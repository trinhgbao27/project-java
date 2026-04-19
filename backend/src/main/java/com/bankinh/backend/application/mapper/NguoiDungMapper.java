package com.bankinh.backend.application.mapper;

import com.bankinh.backend.application.dto.request.NguoiDungRequest;
import com.bankinh.backend.application.dto.response.NguoiDungResponse;
import com.bankinh.backend.domain.model.NguoiDung;
import org.springframework.stereotype.Component;

@Component
public class NguoiDungMapper {

    public NguoiDungResponse toResponse(NguoiDung nguoiDung) {
        return new NguoiDungResponse(
                nguoiDung.getId(),
                nguoiDung.getEmail(),
                nguoiDung.getHoTen(),
                nguoiDung.getVaiTro()
        );
    }

    public NguoiDung toDomain(NguoiDungRequest request) {
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setEmail(request.getEmail());
        nguoiDung.setHoTen(request.getHoTen());
        nguoiDung.setVaiTro(request.getVaiTro());
        return nguoiDung;
    }
}