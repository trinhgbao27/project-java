package com.opticalshop.adapter.persistence.impl;

import com.opticalshop.adapter.persistence.mapper.CommonMapper;
import com.opticalshop.adapter.persistence.repository.PrescriptionJpaRepository;
import com.opticalshop.domain.model.prescription.Prescription;
import com.opticalshop.domain.repository.PrescriptionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PrescriptionRepositoryImpl implements PrescriptionRepository {

    private final PrescriptionJpaRepository jpaRepository;
    private final CommonMapper mapper;

    public PrescriptionRepositoryImpl(PrescriptionJpaRepository jpaRepository, CommonMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Prescription save(Prescription prescription) {
        var saved = jpaRepository.save(mapper.toJpa(prescription));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Prescription> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Prescription> findByCustomerId(UUID customerId) {
        return jpaRepository.findByCustomerId(customerId).stream()
                .map(mapper::toDomain).toList();
    }
}
