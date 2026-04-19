package com.bankinh.backend.application.service;

import com.bankinh.backend.application.dto.request.DonKinhRequest;
import com.bankinh.backend.application.dto.response.DonKinhResponse;
import com.bankinh.backend.application.mapper.DonKinhMapper;
import com.bankinh.backend.common.exception.ResourceNotFoundException;
import com.bankinh.backend.domain.model.DonKinh;
import com.bankinh.backend.domain.repository.DonKinhRepository;
import com.bankinh.backend.domain.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonKinhService {

    private final DonKinhRepository donKinhRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final DonKinhMapper donKinhMapper;

    public DonKinhResponse create(DonKinhRequest request) {
        if (!nguoiDungRepository.existsById(request.getNguoiDungId())) {
            throw new ResourceNotFoundException("NguoiDung not found: " + request.getNguoiDungId());
        }
        DonKinh donKinh = donKinhMapper.toDomain(request);
        return donKinhMapper.toResponse(donKinhRepository.save(donKinh));
    }

    public List<DonKinhResponse> getAllByNguoiDungId(UUID nguoiDungId) {
        if (!nguoiDungRepository.existsById(nguoiDungId)) {
            throw new ResourceNotFoundException("NguoiDung not found: " + nguoiDungId);
        }
        return donKinhRepository.findAllByNguoiDungId(nguoiDungId)
                .stream()
                .map(donKinhMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DonKinhResponse getById(UUID id) {
        return donKinhMapper.toResponse(
                donKinhRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("DonKinh not found: " + id))
        );
    }

    public DonKinhResponse update(UUID id, DonKinhRequest request) {
        DonKinh existing = donKinhRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DonKinh not found: " + id));
        if (!nguoiDungRepository.existsById(request.getNguoiDungId())) {
            throw new ResourceNotFoundException("NguoiDung not found: " + request.getNguoiDungId());
        }
        existing.setNguoiDungId(request.getNguoiDungId());
        existing.setOdCau(request.getOdCau());
        existing.setOsCau(request.getOsCau());
        existing.setKhoangDongTu(request.getKhoangDongTu());
        existing.setFileUrl(request.getFileUrl());
        existing.setGhiChu(request.getGhiChu());
        return donKinhMapper.toResponse(donKinhRepository.save(existing));
    }

    public void delete(UUID id) {
        if (!donKinhRepository.existsById(id)) {
            throw new ResourceNotFoundException("DonKinh not found: " + id);
        }
        donKinhRepository.deleteById(id);
    }
}