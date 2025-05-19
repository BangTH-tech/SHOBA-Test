package com.project_shoba_test.SHOBA_TEST.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserStatus;
import com.project_shoba_test.SHOBA_TEST.repository.UserRepository;
import com.project_shoba_test.SHOBA_TEST.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Order(0)
@RequiredArgsConstructor
public class InactiveCheckFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access_token")) {
                    String token = cookie.getValue();
                    String username = tokenService.extractUsername(token);
                    if (username != null) {
                        Users user = userRepository.existsByUsernameOrEmail(username).orElse(null);
                        if (user != null && user.getStatus() == UserStatus.INACTIVE) {
                            log.info("INACTIVE FILTER: " + user);

                            response.sendError(HttpStatus.FORBIDDEN.value(), "User is inactive");
                            return;
                        }
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
