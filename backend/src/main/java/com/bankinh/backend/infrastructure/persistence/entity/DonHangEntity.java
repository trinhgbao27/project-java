package com.bankinh.backend.infrastructure.persistence.entity;

import com.bankinh.backend.domain.model.TrangThaiDonHang;
import com.bankinh.backend.infrastructure.persistence.converter.TrangThaiDonHangConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "donhang")
public class DonHangEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nguoidung_id", nullable = false)
    private NguoiDungEntity nguoiDung;

    @Type(TrangThaiDonHangConverter.class)
    @Column(name = "trang_thai", nullable = false)
    private TrangThaiDonHang trangThai;

    @Column(name = "tong_tien", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "ho_ten_nguoi_nhan")
    private String hoTenNguoiNhan;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "ly_do_hoan_tra")
    private String lyDoHoanTra;

    @Column(name = "ten_ngan_hang")
    private String tenNganHang;

    @Column(name = "so_tai_khoan")
    private String soTaiKhoan;

    @Column(name = "ten_chu_tai_khoan")
    private String tenChuTaiKhoan;

    @Column(name = "ly_do_tu_choi")
    private String lyDoTuChoi;

    @CreationTimestamp
    @Column(name = "tao_luc", nullable = false, updatable = false)
    private OffsetDateTime taoLuc;

    @UpdateTimestamp
    @Column(name = "cap_nhat_luc", nullable = false)
    private OffsetDateTime capNhatLuc;
}