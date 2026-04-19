package com.bankinh.backend.domain.repository;

import com.bankinh.backend.domain.model.DonHangChiTiet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DonHangChiTietRepository {
    DonHangChiTiet save(DonHangChiTiet donHangChiTiet);
    Optional<DonHangChiTiet> findById(UUID id);
    List<DonHangChiTiet> findAllByDonHangId(UUID donHangId);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}