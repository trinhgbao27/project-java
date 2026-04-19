package com.bankinh.backend.domain.repository;

import com.bankinh.backend.domain.model.NguoiDung;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NguoiDungRepository {
    NguoiDung save(NguoiDung nguoiDung);
    Optional<NguoiDung> findById(UUID id);
    List<NguoiDung> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
    boolean existsByEmail(String email);
}