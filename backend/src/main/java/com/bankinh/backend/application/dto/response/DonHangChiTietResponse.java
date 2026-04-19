package com.bankinh.backend.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonHangChiTietResponse {
    private UUID id;
    private UUID donHangId;
    private UUID sanPhamId;
    private UUID donKinhId;
    private int soLuong;
    private BigDecimal giaBan;
    private OffsetDateTime taoLuc;
    private DonKinhResponse donKinh;
}