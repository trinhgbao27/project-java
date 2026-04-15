package com.bankinh.backend.interfaces.rest;

import com.bankinh.backend.application.dto.request.DonHangChiTietRequest;
import com.bankinh.backend.application.dto.response.DonHangChiTietResponse;
import com.bankinh.backend.application.service.DonHangChiTietService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/don-hang-chi-tiet")
@RequiredArgsConstructor
public class DonHangChiTietController {

    private final DonHangChiTietService donHangChiTietService;

    @PostMapping
    public ResponseEntity<DonHangChiTietResponse> create(@Valid @RequestBody DonHangChiTietRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donHangChiTietService.create(request));
    }

    @GetMapping("/don-hang/{donHangId}")
    public ResponseEntity<List<DonHangChiTietResponse>> getAllByDonHangId(@PathVariable UUID donHangId) {
        return ResponseEntity.ok(donHangChiTietService.getAllByDonHangId(donHangId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        donHangChiTietService.delete(id);
        return ResponseEntity.noContent().build();
    }
}