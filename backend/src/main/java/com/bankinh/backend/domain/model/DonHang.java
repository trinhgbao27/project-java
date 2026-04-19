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
public class DonHang {
    private UUID id;
    private UUID nguoiDungId;
    private TrangThaiDonHang trangThai;
    private BigDecimal tongTien;
    private OffsetDateTime taoLuc;
    private OffsetDateTime capNhatLuc;

    // Thông tin giao hàng
    private String hoTenNguoiNhan;
    private String soDienThoai;
    private String diaChi;

    // Thông tin hoàn trả (khách điền)
    private String lyDoHoanTra;
    private String tenNganHang;
    private String soTaiKhoan;
    private String tenChuTaiKhoan;

    // Lý do từ chối (admin/nhân viên điền)
    private String lyDoTuChoi;
}