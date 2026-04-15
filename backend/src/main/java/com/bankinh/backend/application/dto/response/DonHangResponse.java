package com.bankinh.backend.application.dto.response;

import com.bankinh.backend.domain.model.TrangThaiDonHang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonHangResponse {
    private UUID id;
    private UUID nguoiDungId;
    private TrangThaiDonHang trangThai;
    private BigDecimal tongTien;
    private OffsetDateTime taoLuc;
    private OffsetDateTime capNhatLuc;

    private String hoTenKhachHang;
    private String emailKhachHang;

    private String hoTenNguoiNhan;
    private String soDienThoai;
    private String diaChi;

    private String lyDoHoanTra;
    private String tenNganHang;
    private String soTaiKhoan;
    private String tenChuTaiKhoan;

    private String lyDoTuChoi;
}