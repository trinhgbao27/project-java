package com.bankinh.backend.application.service;

import com.bankinh.backend.application.dto.request.DonHangChiTietRequest;
import com.bankinh.backend.application.dto.response.DonHangChiTietResponse;
import com.bankinh.backend.application.mapper.DonHangChiTietMapper;
import com.bankinh.backend.common.exception.ResourceNotFoundException;
import com.bankinh.backend.domain.model.DonHang;
import com.bankinh.backend.domain.model.DonHangChiTiet;
import com.bankinh.backend.domain.repository.DonHangChiTietRepository;
import com.bankinh.backend.domain.repository.DonHangRepository;
import com.bankinh.backend.domain.repository.DonKinhRepository;
import com.bankinh.backend.domain.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonHangChiTietService {

    private final DonHangChiTietRepository donHangChiTietRepository;
    private final DonHangRepository donHangRepository;
    private final SanPhamRepository sanPhamRepository;
    private final DonKinhRepository donKinhRepository;
    private final DonHangChiTietMapper donHangChiTietMapper;

    public DonHangChiTietResponse create(DonHangChiTietRequest request) {
        if (!donHangRepository.existsById(request.getDonHangId())) {
            throw new ResourceNotFoundException("DonHang not found: " + request.getDonHangId());
        }
        if (!sanPhamRepository.existsById(request.getSanPhamId())) {
            throw new ResourceNotFoundException("SanPham not found: " + request.getSanPhamId());
        }
        if (request.getDonKinhId() != null && !donKinhRepository.existsById(request.getDonKinhId())) {
            throw new ResourceNotFoundException("DonKinh not found: " + request.getDonKinhId());
        }

        DonHangChiTiet chiTiet = donHangChiTietMapper.toDomain(request);
        DonHangChiTietResponse response = donHangChiTietMapper.toResponse(donHangChiTietRepository.save(chiTiet));

        capNhatTongTien(request.getDonHangId());

        return response;
    }

    public List<DonHangChiTietResponse> getAllByDonHangId(UUID donHangId) {
        if (!donHangRepository.existsById(donHangId)) {
            throw new ResourceNotFoundException("DonHang not found: " + donHangId);
        }
        return donHangChiTietRepository.findAllByDonHangId(donHangId)
                .stream()
                .map(donHangChiTietMapper::toResponse)
                .collect(Collectors.toList());
    }

    public void delete(UUID id) {
        DonHangChiTiet chiTiet = donHangChiTietRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DonHangChiTiet not found: " + id));

        UUID donHangId = chiTiet.getDonHangId();
        donHangChiTietRepository.deleteById(id);

        capNhatTongTien(donHangId);
    }

    private void capNhatTongTien(UUID donHangId) {
        DonHang donHang = donHangRepository.findById(donHangId)
                .orElseThrow(() -> new ResourceNotFoundException("DonHang not found: " + donHangId));

        BigDecimal tongTien = donHangChiTietRepository.findAllByDonHangId(donHangId)
                .stream()
                .map(ct -> ct.getGiaBan().multiply(BigDecimal.valueOf(ct.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        donHang.setTongTien(tongTien);
        donHangRepository.save(donHang);
    }
}