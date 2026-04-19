package com.bankinh.backend.application.dto.request;

import com.bankinh.backend.domain.model.VaiTro;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NguoiDungRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String hoTen;

    @NotNull
    private VaiTro vaiTro;
}