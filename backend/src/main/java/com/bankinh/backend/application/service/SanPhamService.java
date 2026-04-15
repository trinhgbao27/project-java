package com.bankinh.backend.application.service;

import com.bankinh.backend.application.dto.request.SanPhamRequest;
import com.bankinh.backend.application.dto.response.SanPhamResponse;
import com.bankinh.backend.application.mapper.SanPhamMapper;
import com.bankinh.backend.common.exception.ResourceNotFoundException;
import com.bankinh.backend.domain.model.SanPham;
import com.bankinh.backend.domain.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanPhamService {

    private final SanPhamRepository sanPhamRepository;
    private final SanPhamMapper sanPhamMapper;

    public SanPhamResponse create(SanPhamRequest request) {
        SanPham sanPham = sanPhamMapper.toDomain(request);
        return sanPhamMapper.toResponse(sanPhamRepository.save(sanPham));
    }

    public List<SanPhamResponse> getAll() {
        return sanPhamRepository.findAll()
                .stream()
                .map(sanPhamMapper::toResponse)
                .collect(Collectors.toList());
    }

    public SanPhamResponse getById(UUID id) {
        return sanPhamMapper.toResponse(
                sanPhamRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("SanPham not found: " + id))
        );
    }

    public SanPhamResponse update(UUID id, SanPhamRequest request) {
        SanPham existing = sanPhamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SanPham not found: " + id));
        existing.setTen(request.getTen());
        existing.setLoai(request.getLoai());
        existing.setGia(request.getGia());
        existing.setSoLuongTon(request.getSoLuongTon());
        if (request.getHinhAnhA() != null) {
            existing.setHinhAnhA(request.getHinhAnhA());
        }
        if (request.getHinhAnhB() != null) {
            existing.setHinhAnhB(request.getHinhAnhB());
        }
        return sanPhamMapper.toResponse(sanPhamRepository.save(existing));
    }

    public void delete(UUID id) {
        if (!sanPhamRepository.existsById(id)) {
            throw new ResourceNotFoundException("SanPham not found: " + id);
        }
        sanPhamRepository.deleteById(id);
    }
}