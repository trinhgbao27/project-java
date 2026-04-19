package com.bankinh.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPham {
    private UUID id;
    private String ten;
    private LoaiSanPham loai;
    private BigDecimal gia;
    private int soLuongTon;
    private String hinhAnhA;
    private String hinhAnhB;
    private OffsetDateTime taoLuc;
    private OffsetDateTime capNhatLuc;
}