package com.bankinh.backend.domain.repository;

import com.bankinh.backend.domain.model.DonHang;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DonHangRepository {
    DonHang save(DonHang donHang);
    Optional<DonHang> findById(UUID id);
    List<DonHang> findAll();
    List<DonHang> findAllByNguoiDungId(UUID nguoiDungId);
    boolean existsById(UUID id);
}