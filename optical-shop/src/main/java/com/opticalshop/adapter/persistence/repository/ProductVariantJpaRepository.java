package com.opticalshop.adapter.persistence.repository;

import com.opticalshop.adapter.persistence.entity.ProductVariantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductVariantJpaRepository extends JpaRepository<ProductVariantJpaEntity, UUID> {
}
