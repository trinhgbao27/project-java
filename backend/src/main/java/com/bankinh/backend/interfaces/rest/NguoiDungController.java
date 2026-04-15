package com.bankinh.backend.interfaces.rest;

import com.bankinh.backend.application.dto.request.NguoiDungRequest;
import com.bankinh.backend.application.dto.response.NguoiDungResponse;
import com.bankinh.backend.application.service.NguoiDungService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/nguoi-dung")
@RequiredArgsConstructor
public class NguoiDungController {

    private final NguoiDungService nguoiDungService;

    @PostMapping
    public ResponseEntity<NguoiDungResponse> create(@Valid @RequestBody NguoiDungRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(nguoiDungService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<NguoiDungResponse>> getAll() {
        return ResponseEntity.ok(nguoiDungService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NguoiDungResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(nguoiDungService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NguoiDungResponse> update(@PathVariable UUID id,
                                                     @Valid @RequestBody NguoiDungRequest request) {
        return ResponseEntity.ok(nguoiDungService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        nguoiDungService.delete(id);
        return ResponseEntity.noContent().build();
    }
}