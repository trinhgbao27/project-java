package com.bankinh.backend.application.dto.response;

import com.bankinh.backend.domain.model.VaiTro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDungResponse {
    private UUID id;
    private String email;
    private String hoTen;
    private VaiTro vaiTro;
}