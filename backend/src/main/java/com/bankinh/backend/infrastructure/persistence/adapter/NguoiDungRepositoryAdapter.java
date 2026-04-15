package com.bankinh.backend.infrastructure.persistence.adapter;

import com.bankinh.backend.domain.model.NguoiDung;
import com.bankinh.backend.domain.repository.NguoiDungRepository;
import com.bankinh.backend.infrastructure.persistence.mapper.NguoiDungEntityMapper;
import com.bankinh.backend.infrastructure.persistence.repository.NguoiDungJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NguoiDungRepositoryAdapter implements NguoiDungRepository {

    private final NguoiDungJpaRepository jpaRepository;
    private final NguoiDungEntityMapper entityMapper;

    @Override
    public NguoiDung save(NguoiDung nguoiDung) {
        return entityMapper.toDomain(jpaRepository.save(entityMapper.toEntity(nguoiDung)));
    }

    @Override
    public Optional<NguoiDung> findById(UUID id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public List<NguoiDung> findAll() {
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

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}