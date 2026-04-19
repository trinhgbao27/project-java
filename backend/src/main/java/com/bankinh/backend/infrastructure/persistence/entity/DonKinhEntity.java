package com.bankinh.backend.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "don_kinh")
public class DonKinhEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nguoidung_id", nullable = false)
    private NguoiDungEntity nguoiDung;

    @Column(name = "od_cau", precision = 5, scale = 2)
    private BigDecimal odCau;

    @Column(name = "os_cau", precision = 5, scale = 2)
    private BigDecimal osCau;

    @Column(name = "khoang_dong_tu", precision = 5, scale = 2)
    private BigDecimal khoangDongTu;

    @Column(name = "file_url", columnDefinition = "text")
    private String fileUrl;

    @Column(name = "ghi_chu", columnDefinition = "text")
    private String ghiChu;

    @CreationTimestamp
    @Column(name = "tao_luc", nullable = false, updatable = false)
    private OffsetDateTime taoLuc;

    @UpdateTimestamp
    @Column(name = "cap_nhat_luc", nullable = false)
    private OffsetDateTime capNhatLuc;
}