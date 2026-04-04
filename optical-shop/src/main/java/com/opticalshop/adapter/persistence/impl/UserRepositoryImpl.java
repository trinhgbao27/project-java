package com.opticalshop.adapter.persistence.impl;

import com.opticalshop.adapter.persistence.mapper.CommonMapper;
import com.opticalshop.adapter.persistence.repository.UserJpaRepository;
import com.opticalshop.domain.model.user.User;
import com.opticalshop.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final CommonMapper mapper;

    public UserRepositoryImpl(UserJpaRepository jpaRepository, CommonMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        var saved = jpaRepository.save(mapper.toJpa(user));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
