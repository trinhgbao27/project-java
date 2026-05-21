package com.bankinh.backend.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Các đường dẫn public, không cần token
        if (path.startsWith("/api/auth") ||
                (path.startsWith("/api/san-pham") && request.getMethod().equals("GET"))) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Thiếu token");
            return;
        }

        String token = header.substring(7);
        if (!jwtUtil.isValid(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token không hợp lệ hoặc đã hết hạn");
            return;
        }

        // Gắn thông tin user vào request để các controller dùng
        request.setAttribute("nguoiDungId", jwtUtil.extractNguoiDungId(token));
        request.setAttribute("vaiTro", jwtUtil.extractVaiTro(token));

        chain.doFilter(request, response);
    }
}