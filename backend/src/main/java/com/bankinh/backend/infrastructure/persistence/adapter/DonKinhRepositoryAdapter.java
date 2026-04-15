package com.bankinh.backend.infrastructure.persistence.adapter;

import com.bankinh.backend.domain.model.DonKinh;
import com.bankinh.backend.domain.repository.DonKinhRepository;
import com.bankinh.backend.infrastructure.persistence.mapper.DonKinhEntityMapper;
import com.bankinh.backend.infrastructure.persistence.repository.DonKinhJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DonKinhRepositoryAdapter implements DonKinhRepository {

    private final DonKinhJpaRepository jpaRepository;
    private final DonKinhEntityMapper entityMapper;

    @Override
    public DonKinh save(DonKinh donKinh) {
        return entityMapper.toDomain(jpaRepository.save(entityMapper.toEntity(donKinh)));
    }

    @Override
    public Optional<DonKinh> findById(UUID id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public List<DonKinh> findAllByNguoiDungId(UUID nguoiDungId) {
        return jpaRepository.findAllByNguoiDung_Id(nguoiDungId)
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