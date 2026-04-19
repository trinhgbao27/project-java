package com.bankinh.backend.domain.repository;

import com.bankinh.backend.domain.model.SanPham;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SanPhamRepository {
    SanPham save(SanPham sanPham);
    Optional<SanPham> findById(UUID id);
    List<SanPham> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}