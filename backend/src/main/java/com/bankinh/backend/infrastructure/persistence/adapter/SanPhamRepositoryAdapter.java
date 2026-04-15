package com.bankinh.backend.infrastructure.persistence.adapter;

import com.bankinh.backend.domain.model.SanPham;
import com.bankinh.backend.domain.repository.SanPhamRepository;
import com.bankinh.backend.infrastructure.persistence.mapper.SanPhamEntityMapper;
import com.bankinh.backend.infrastructure.persistence.repository.SanPhamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SanPhamRepositoryAdapter implements SanPhamRepository {

    private final SanPhamJpaRepository jpaRepository;
    private final SanPhamEntityMapper entityMapper;

    @Override
    public SanPham save(SanPham sanPham) {
        return entityMapper.toDomain(jpaRepository.save(entityMapper.toEntity(sanPham)));
    }

    @Override
    public Optional<SanPham> findById(UUID id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public List<SanPham> findAll() {
        return jpaRepository.findAll().stream().map(entityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}