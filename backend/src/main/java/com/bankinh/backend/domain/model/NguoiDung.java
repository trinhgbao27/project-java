package com.bankinh.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDung {
    private UUID id;
    private String email;
    private String hoTen;
    private VaiTro vaiTro;
    private OffsetDateTime taoLuc;
    private OffsetDateTime capNhatLuc;
}