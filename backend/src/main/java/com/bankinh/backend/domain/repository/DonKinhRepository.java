package com.bankinh.backend.domain.repository;

import com.bankinh.backend.domain.model.DonKinh;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DonKinhRepository {
    DonKinh save(DonKinh donKinh);
    Optional<DonKinh> findById(UUID id);
    List<DonKinh> findAllByNguoiDungId(UUID nguoiDungId);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}