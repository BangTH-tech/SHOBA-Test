package com.project_shoba_test.SHOBA_TEST.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.project_shoba_test.SHOBA_TEST.model.entity.HttpLog;
import com.project_shoba_test.SHOBA_TEST.service.log.HttpLogService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    private final HttpLogService httpLogService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        String requestBody = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);

        String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

        String method = request.getMethod();
        String url = request.getRequestURI();
        int status = response.getStatus();
        LocalDateTime now = LocalDateTime.now();

        // Lưu log vào MongoDB
        if (!url.contains("/log-list")) {
            HttpLog log = new HttpLog(null, method, url, requestBody, responseBody, status, now);
            httpLogService.save(log);
        }

        // Copy lại response body vào output stream
        wrappedResponse.copyBodyToResponse();
    }

}
