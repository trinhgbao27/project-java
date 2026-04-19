package com.bankinh.backend.infrastructure.persistence.entity;

import com.bankinh.backend.domain.model.LoaiSanPham;
import com.bankinh.backend.infrastructure.persistence.converter.LoaiSanPhamConverter;
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
@Table(name = "sanpham")
public class SanPhamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Type(LoaiSanPhamConverter.class)
    @Column(name = "loai", nullable = false)
    private LoaiSanPham loai;

    @Column(name = "gia", nullable = false, precision = 12, scale = 2)
    private BigDecimal gia;

    @Column(name = "so_luong_ton", nullable = false)
    private int soLuongTon;

    @Column(name = "hinh_anh_a")
    private String hinhAnhA;

    @Column(name = "hinh_anh_b")
    private String hinhAnhB;

    @CreationTimestamp
    @Column(name = "tao_luc", nullable = false, updatable = false)
    private OffsetDateTime taoLuc;

    @UpdateTimestamp
    @Column(name = "cap_nhat_luc", nullable = false)
    private OffsetDateTime capNhatLuc;
}