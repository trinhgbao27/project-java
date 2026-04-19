package com.bankinh.backend.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class DonKinhRequest {

    @NotNull
    private UUID nguoiDungId;

    private BigDecimal odCau;
    private BigDecimal osCau;
    private BigDecimal khoangDongTu;
    private String fileUrl;
    private String ghiChu;
}