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
public class DonKinhResponse {
    private UUID id;
    private UUID nguoiDungId;
    private BigDecimal odCau;
    private BigDecimal osCau;
    private BigDecimal khoangDongTu;
    private String fileUrl;
    private String ghiChu;
    private OffsetDateTime taoLuc;
    private OffsetDateTime capNhatLuc;
}