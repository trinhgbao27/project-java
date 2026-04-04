package com.opticalshop.adapter.web.customer;

import com.opticalshop.adapter.web.dto.request.LoginRequest;
import com.opticalshop.adapter.web.dto.request.RegisterRequest;
import com.opticalshop.adapter.web.dto.response.ApiResponse;
import com.opticalshop.adapter.web.dto.response.AuthResponse;
import com.opticalshop.domain.exception.DomainException;
import com.opticalshop.domain.model.user.User;
import com.opticalshop.domain.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AccountController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // JwtUtil được inject từ infrastructure layer
    private final com.opticalshop.infrastructure.security.JwtUtil jwtUtil;

    public AccountController(UserRepository userRepository,
                              PasswordEncoder passwordEncoder,
                              com.opticalshop.infrastructure.security.JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * POST /api/v1/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new DomainException("Email already registered: " + request.email());
        }

        User user = User.create(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                request.fullName(),
                request.phone(),
                request.address()
        );

        User saved = userRepository.save(user);
        String token = jwtUtil.generateToken(saved.getId().toString(), saved.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(new AuthResponse(token, saved.getUsername(), saved.getEmail())));
    }

    /**
     * POST /api/v1/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new DomainException("Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new DomainException("Invalid username or password");
        }

        if (!user.isActive()) {
            throw new DomainException("Account is deactivated");
        }

        String token = jwtUtil.generateToken(user.getId().toString(), user.getUsername());
        return ResponseEntity.ok(
                ApiResponse.ok(new AuthResponse(token, user.getUsername(), user.getEmail())));
    }
}
