package com.bankinh.backend.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class DonHangChiTietRequest {

    @NotNull
    private UUID donHangId;

    @NotNull
    private UUID sanPhamId;

    private UUID donKinhId;

    @Min(1)
    private int soLuong;

    @NotNull
    private BigDecimal giaBan;
}