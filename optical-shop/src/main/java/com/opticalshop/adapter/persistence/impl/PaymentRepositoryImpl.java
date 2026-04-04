package com.opticalshop.adapter.persistence.impl;

import com.opticalshop.adapter.persistence.mapper.CommonMapper;
import com.opticalshop.adapter.persistence.repository.PaymentJpaRepository;
import com.opticalshop.domain.model.payment.Payment;
import com.opticalshop.domain.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;
    private final CommonMapper mapper;

    public PaymentRepositoryImpl(PaymentJpaRepository jpaRepository, CommonMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Payment save(Payment payment) {
        var saved = jpaRepository.save(mapper.toJpa(payment));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return jpaRepository.findByOrderId(orderId).map(mapper::toDomain);
    }
}
