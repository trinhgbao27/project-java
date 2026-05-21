package com.bankinh.backend.interfaces.rest;

import com.bankinh.backend.application.dto.response.NguoiDungResponse;
import com.bankinh.backend.application.mapper.NguoiDungMapper;
import com.bankinh.backend.common.exception.ResourceNotFoundException;
import com.bankinh.backend.domain.repository.NguoiDungRepository;
import com.bankinh.backend.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final NguoiDungRepository nguoiDungRepository;
    private final NguoiDungMapper nguoiDungMapper;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        var nguoiDung = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email không tồn tại trong hệ thống"));

        String token = jwtUtil.generateToken(nguoiDung.getId(), nguoiDung.getVaiTro());
        NguoiDungResponse userInfo = nguoiDungMapper.toResponse(nguoiDung);

        return ResponseEntity.ok(Map.of("token", token, "user", userInfo));
    }
}