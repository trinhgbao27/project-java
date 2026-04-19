package com.bankinh.backend.interfaces.rest;

import com.bankinh.backend.application.dto.request.DonKinhRequest;
import com.bankinh.backend.application.dto.response.DonKinhResponse;
import com.bankinh.backend.application.service.DonKinhService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/don-kinh")
@RequiredArgsConstructor
public class DonKinhController {

    private final DonKinhService donKinhService;

    @PostMapping
    public ResponseEntity<DonKinhResponse> create(@Valid @RequestBody DonKinhRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donKinhService.create(request));
    }

    @GetMapping("/nguoi-dung/{nguoiDungId}")
    public ResponseEntity<List<DonKinhResponse>> getAllByNguoiDungId(@PathVariable UUID nguoiDungId) {
        return ResponseEntity.ok(donKinhService.getAllByNguoiDungId(nguoiDungId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonKinhResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(donKinhService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonKinhResponse> update(@PathVariable UUID id,
                                                   @Valid @RequestBody DonKinhRequest request) {
        return ResponseEntity.ok(donKinhService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        donKinhService.delete(id);
        return ResponseEntity.noContent().build();
    }
}