package com.project_shoba_test.SHOBA_TEST.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.project_shoba_test.SHOBA_TEST.model.entity.HttpLog;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserStatus;
import com.project_shoba_test.SHOBA_TEST.repository.UserRepository;
import com.project_shoba_test.SHOBA_TEST.service.log.HttpLogService;
import com.project_shoba_test.SHOBA_TEST.service.token.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    private final HttpLogService httpLogService;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Users user = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access_token")) {
                    String token = cookie.getValue();
                    String username = null;
                    try {
                        username = tokenService.extractUsername(token);
                    } catch (Exception ex) {

                    }

                    if (username != null) {
                        user = userRepository.existsByUsernameOrEmail(username).orElse(null);
                        if (user != null && user.getStatus() == UserStatus.INACTIVE) {

                            response.sendError(HttpStatus.FORBIDDEN.value(), "User is inactive");
                            return;
                        }
                    }
                }
            }
        }
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        String requestBody = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);

        String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

        String method = request.getMethod();
        String url = request.getRequestURI();
        int status = response.getStatus();
        Date now = new Date();

        // Lưu log vào MongoDB
        if (!url.contains("/log-list")) {
            HttpLog log = new HttpLog(null, method, url, requestBody, responseBody, status, now, user != null ? user.getUsername() : "");
            httpLogService.save(log);
        }

        // Copy lại response body vào output stream
        wrappedResponse.copyBodyToResponse();
    }

}
