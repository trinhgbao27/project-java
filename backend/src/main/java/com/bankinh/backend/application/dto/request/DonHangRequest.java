package com.bankinh.backend.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class DonHangRequest {

    @NotNull
    private UUID nguoiDungId;

    @NotBlank
    private String hoTenNguoiNhan;

    @NotBlank
    private String soDienThoai;

    @NotBlank
    private String diaChi;
}