package com.bankinh.backend.application.dto.request;

import com.bankinh.backend.domain.model.LoaiSanPham;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SanPhamRequest {

    @NotBlank
    private String ten;

    @NotNull
    private LoaiSanPham loai;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal gia;

    @Min(0)
    private int soLuongTon;

    private String hinhAnhA;

    private String hinhAnhB;

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public LoaiSanPham getLoai() { return loai; }
    public void setLoai(LoaiSanPham loai) { this.loai = loai; }

    public BigDecimal getGia() { return gia; }
    public void setGia(BigDecimal gia) { this.gia = gia; }

    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }

    public String getHinhAnhA() { return hinhAnhA; }
    public void setHinhAnhA(String hinhAnhA) { this.hinhAnhA = hinhAnhA; }

    public String getHinhAnhB() { return hinhAnhB; }
    public void setHinhAnhB(String hinhAnhB) { this.hinhAnhB = hinhAnhB; }
}