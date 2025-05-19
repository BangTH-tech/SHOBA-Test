package com.project_shoba_test.SHOBA_TEST.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project_shoba_test.SHOBA_TEST.filter.InactiveCheckFilter;
import com.project_shoba_test.SHOBA_TEST.filter.JwtFilter;
import com.project_shoba_test.SHOBA_TEST.handler.CustomAccessDeniedHandler;
import com.project_shoba_test.SHOBA_TEST.handler.CustomAuthenticationEntryPoint;
import com.project_shoba_test.SHOBA_TEST.handler.JwtAuthenticationFailureHandler;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserRole;
import com.project_shoba_test.SHOBA_TEST.repository.UserRepository;
import com.project_shoba_test.SHOBA_TEST.service.TokenService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenService tokenService;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final String[] publicUrls = {
            "/api/v1/auth/**"
    };

    private final String[] guestUrl = {
            "/login/**"
    };

    private final JwtFilter jwtFilter;

    private final InactiveCheckFilter inactiveCheckFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers("/api/v1/admin/**")
                                .hasAuthority(UserRole.ADMIN.toString())
                                .requestMatchers(publicUrls).permitAll()
                                .anyRequest()
                                .authenticated())
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(
                        exception -> exception
                                .accessDeniedHandler(new CustomAccessDeniedHandler())
                                .authenticationEntryPoint(
                                        new CustomAuthenticationEntryPoint()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(inactiveCheckFilter, JwtFilter.class)
                .build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }



}
