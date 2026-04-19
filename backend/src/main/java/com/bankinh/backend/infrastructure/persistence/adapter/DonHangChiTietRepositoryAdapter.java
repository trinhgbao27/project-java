package com.bankinh.backend.infrastructure.persistence.adapter;

import com.bankinh.backend.domain.model.DonHangChiTiet;
import com.bankinh.backend.domain.repository.DonHangChiTietRepository;
import com.bankinh.backend.infrastructure.persistence.mapper.DonHangChiTietEntityMapper;
import com.bankinh.backend.infrastructure.persistence.repository.DonHangChiTietJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DonHangChiTietRepositoryAdapter implements DonHangChiTietRepository {

    private final DonHangChiTietJpaRepository jpaRepository;
    private final DonHangChiTietEntityMapper entityMapper;

    @Override
    public DonHangChiTiet save(DonHangChiTiet donHangChiTiet) {
        return entityMapper.toDomain(jpaRepository.save(entityMapper.toEntity(donHangChiTiet)));
    }

    @Override
    public Optional<DonHangChiTiet> findById(UUID id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public List<DonHangChiTiet> findAllByDonHangId(UUID donHangId) {
        return jpaRepository.findAllByDonHang_Id(donHangId)
                .stream()
                .map(entityMapper::toDomain)
                .collect(Collectors.toList());
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