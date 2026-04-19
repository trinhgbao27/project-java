package com.bankinh.backend.interfaces.rest;

import com.bankinh.backend.application.dto.request.CapNhatTrangThaiRequest;
import com.bankinh.backend.application.dto.request.DonHangRequest;
import com.bankinh.backend.application.dto.request.TuChoiHoanTraRequest;
import com.bankinh.backend.application.dto.request.YeuCauHoanTraRequest;
import com.bankinh.backend.application.dto.response.DonHangResponse;
import com.bankinh.backend.application.service.DonHangService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/don-hang")
@RequiredArgsConstructor
public class DonHangController {

    private final DonHangService donHangService;

    @PostMapping
    public ResponseEntity<DonHangResponse> create(@Valid @RequestBody DonHangRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donHangService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<DonHangResponse>> getAll() {
        return ResponseEntity.ok(donHangService.getAll());
    }

    @GetMapping("/nguoi-dung/{nguoiDungId}")
    public ResponseEntity<List<DonHangResponse>> getAllByNguoiDungId(@PathVariable UUID nguoiDungId) {
        return ResponseEntity.ok(donHangService.getAllByNguoiDungId(nguoiDungId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonHangResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(donHangService.getById(id));
    }

    @PutMapping("/{id}/trang-thai")
    public ResponseEntity<DonHangResponse> capNhatTrangThai(@PathVariable UUID id,
                                                             @Valid @RequestBody CapNhatTrangThaiRequest request) {
        return ResponseEntity.ok(donHangService.capNhatTrangThai(id, request));
    }

    @PutMapping("/{id}/huy")
    public ResponseEntity<DonHangResponse> huyDon(@PathVariable UUID id,
                                                   @RequestParam UUID nguoiDungId) {
        return ResponseEntity.ok(donHangService.huyDon(id, nguoiDungId));
    }

    @PutMapping("/{id}/hoan-tra")
    public ResponseEntity<DonHangResponse> yeuCauHoanTra(@PathVariable UUID id,
                                                          @RequestParam UUID nguoiDungId,
                                                          @Valid @RequestBody YeuCauHoanTraRequest request) {
        return ResponseEntity.ok(donHangService.yeuCauHoanTra(id, nguoiDungId, request));
    }

    @PutMapping("/{id}/tu-choi-hoan-tra")
    public ResponseEntity<DonHangResponse> tuChoiHoanTra(@PathVariable UUID id,
                                                          @Valid @RequestBody TuChoiHoanTraRequest request) {
        return ResponseEntity.ok(donHangService.tuChoiHoanTra(id, request));
    }
}