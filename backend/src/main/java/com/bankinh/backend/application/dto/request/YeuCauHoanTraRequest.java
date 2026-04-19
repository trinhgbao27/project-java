package com.bankinh.backend.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class YeuCauHoanTraRequest {

    @NotBlank
    private String lyDo;

    @NotBlank
    private String tenNganHang;

    @NotBlank
    private String soTaiKhoan;

    @NotBlank
    private String tenChuTaiKhoan;
}