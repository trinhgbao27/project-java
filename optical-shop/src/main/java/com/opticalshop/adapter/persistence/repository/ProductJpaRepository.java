package com.opticalshop.adapter.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opticalshop.adapter.persistence.entity.ProductJpaEntity;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, UUID> {
    Optional<ProductJpaEntity> findBySlug(String slug);
    List<ProductJpaEntity> findByCategoryId(int categoryId);
    List<ProductJpaEntity> findByActiveTrue();
}
