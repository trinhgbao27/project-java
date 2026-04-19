package com.bankinh.backend.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "donhang_chitiet")
public class DonHangChiTietEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "donhang_id", nullable = false)
    private DonHangEntity donHang;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sanpham_id", nullable = false)
    private SanPhamEntity sanPham;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "don_kinh_id", nullable = true)
    private DonKinhEntity donKinh;

    @Column(name = "so_luong", nullable = false)
    private int soLuong;

    @Column(name = "gia_ban", nullable = false, precision = 12, scale = 2)
    private BigDecimal giaBan;

    @CreationTimestamp
    @Column(name = "tao_luc", nullable = false, updatable = false)
    private OffsetDateTime taoLuc;
}