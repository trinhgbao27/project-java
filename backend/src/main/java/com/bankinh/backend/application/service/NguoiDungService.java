package com.bankinh.backend.application.service;

import com.bankinh.backend.application.dto.request.NguoiDungRequest;
import com.bankinh.backend.application.dto.response.NguoiDungResponse;
import com.bankinh.backend.application.mapper.NguoiDungMapper;
import com.bankinh.backend.common.exception.ResourceNotFoundException;
import com.bankinh.backend.domain.model.NguoiDung;
import com.bankinh.backend.domain.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NguoiDungService {

    private final NguoiDungRepository nguoiDungRepository;
    private final NguoiDungMapper nguoiDungMapper;

    public NguoiDungResponse create(NguoiDungRequest request) {
        if (nguoiDungRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại: " + request.getEmail());
        }
        NguoiDung nguoiDung = nguoiDungMapper.toDomain(request);
        return nguoiDungMapper.toResponse(nguoiDungRepository.save(nguoiDung));
    }

    public List<NguoiDungResponse> getAll() {
        return nguoiDungRepository.findAll()
                .stream()
                .map(nguoiDungMapper::toResponse)
                .collect(Collectors.toList());
    }

    public NguoiDungResponse getById(UUID id) {
        return nguoiDungMapper.toResponse(
                nguoiDungRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("NguoiDung not found: " + id))
        );
    }

    public NguoiDungResponse update(UUID id, NguoiDungRequest request) {
        NguoiDung existing = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NguoiDung not found: " + id));
        existing.setEmail(request.getEmail());
        existing.setHoTen(request.getHoTen());
        existing.setVaiTro(request.getVaiTro());
        return nguoiDungMapper.toResponse(nguoiDungRepository.save(existing));
    }

    public void delete(UUID id) {
        if (!nguoiDungRepository.existsById(id)) {
            throw new ResourceNotFoundException("NguoiDung not found: " + id);
        }
        nguoiDungRepository.deleteById(id);
    }
}