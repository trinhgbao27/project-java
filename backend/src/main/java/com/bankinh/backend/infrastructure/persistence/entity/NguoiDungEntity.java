package com.bankinh.backend.infrastructure.persistence.entity;

import com.bankinh.backend.domain.model.VaiTro;
import com.bankinh.backend.infrastructure.persistence.converter.VaiTroConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "nguoidung")
public class NguoiDungEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Type(VaiTroConverter.class)
    @Column(name = "vai_tro", nullable = false)
    private VaiTro vaiTro;

    @CreationTimestamp
    @Column(name = "tao_luc", nullable = false, updatable = false)
    private OffsetDateTime taoLuc;

    @UpdateTimestamp
    @Column(name = "cap_nhat_luc", nullable = false)
    private OffsetDateTime capNhatLuc;
}