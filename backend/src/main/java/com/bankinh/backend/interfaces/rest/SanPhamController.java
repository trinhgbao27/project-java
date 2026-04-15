package com.bankinh.backend.interfaces.rest;

import com.bankinh.backend.application.dto.request.SanPhamRequest;
import com.bankinh.backend.application.dto.response.SanPhamResponse;
import com.bankinh.backend.application.service.SanPhamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/san-pham")
@RequiredArgsConstructor
public class SanPhamController {

    private final SanPhamService sanPhamService;

    @PostMapping
    public ResponseEntity<SanPhamResponse> create(@Valid @RequestBody SanPhamRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sanPhamService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<SanPhamResponse>> getAll() {
        return ResponseEntity.ok(sanPhamService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(sanPhamService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SanPhamResponse> update(@PathVariable UUID id,
                                                   @Valid @RequestBody SanPhamRequest request) {
        return ResponseEntity.ok(sanPhamService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sanPhamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}