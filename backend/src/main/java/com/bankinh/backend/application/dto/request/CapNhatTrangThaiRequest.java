package com.bankinh.backend.application.dto.request;

import com.bankinh.backend.domain.model.TrangThaiDonHang;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CapNhatTrangThaiRequest {

    @NotNull
    private TrangThaiDonHang trangThai;
}