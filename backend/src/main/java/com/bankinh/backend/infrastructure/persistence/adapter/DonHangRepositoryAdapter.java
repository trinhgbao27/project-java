package com.bankinh.backend.infrastructure.persistence.adapter;

import com.bankinh.backend.domain.model.DonHang;
import com.bankinh.backend.domain.repository.DonHangRepository;
import com.bankinh.backend.infrastructure.persistence.mapper.DonHangEntityMapper;
import com.bankinh.backend.infrastructure.persistence.repository.DonHangJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DonHangRepositoryAdapter implements DonHangRepository {

    private final DonHangJpaRepository jpaRepository;
    private final DonHangEntityMapper entityMapper;

    @Override
    public DonHang save(DonHang donHang) {
        return entityMapper.toDomain(jpaRepository.save(entityMapper.toEntity(donHang)));
    }

    @Override
    public Optional<DonHang> findById(UUID id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public List<DonHang> findAll() {
        return jpaRepository.findAll().stream().map(entityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<DonHang> findAllByNguoiDungId(UUID nguoiDungId) {
        return jpaRepository.findAllByNguoiDung_Id(nguoiDungId)
                .stream()
                .map(entityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}