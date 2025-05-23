package com.project_shoba_test.SHOBA_TEST.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_shoba_test.SHOBA_TEST.exception.ForbiddenException;
import com.project_shoba_test.SHOBA_TEST.handler.JwtAuthenticationFailureHandler;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserRole;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserStatus;
import com.project_shoba_test.SHOBA_TEST.repository.UserRepository;
import com.project_shoba_test.SHOBA_TEST.service.token.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Order(1)
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtAuthenticationFailureHandler authenticationFailureHandler;

    private static List<String> skipFilterUrls = Arrays.asList(
            "/api/v1/auth/**");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = null;

        String username = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access_token")) {
                    accessToken = cookie.getValue();
                    username = tokenService.extractUsername(accessToken);
                }
            }
        }
        log.info(username);
        if (accessToken != null && !accessToken.equals("")) {
            if ((username != null && !username.equals("")) &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userRepository.existsByUsernameOrEmail(username).isPresent()
                        && tokenService.validateToken(accessToken)) {
                    Users user = userRepository.existsByUsernameOrEmail(username).get();
          
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    authenticationFailureHandler.onAuthenticationFailure(request, response,
                            new AuthenticationException("Invalid or expired JWT token") {
                            });
                    return;
                }
            }
        } else {
            authenticationFailureHandler.onAuthenticationFailure(request, response,
                    new AuthenticationException("Invalid or expired JWT token") {
                    });
            return;
        }
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            return true;
        }

        Cookie[] cookies = request.getCookies();
        if (!request.getRequestURI().equals("/api/v1/auth/login/oauth2/google")) {
            try {
                String accessToken = "";
                String username = "";
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("access_token")) {
                            accessToken = cookie.getValue();
                            username = tokenService.extractUsername(accessToken);
                        }
                    }
                }
                if (accessToken != null && !accessToken.equals("")) {
                    if ((username != null && !username.equals("")) &&
                            SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        if (userRepository.existsByUsernameOrEmail(username).isPresent()
                                && tokenService.validateToken(accessToken)) {
                            Users user = userRepository.existsByUsernameOrEmail(username).get();
       
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                }
            } catch (Exception ex) {
                return skipFilterUrls.stream().anyMatch(url -> new AntPathRequestMatcher(url).matches(request));

            }
        }
        return skipFilterUrls.stream().anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
    }

}
