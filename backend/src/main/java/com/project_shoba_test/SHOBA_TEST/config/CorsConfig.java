package com.project_shoba_test.SHOBA_TEST.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

        @Value("${spring.application.url}")
        private String applicationUrl;

        @Value("${spring.application.fe-url}")
        private String frontendUrl;

        @Value("${spring.application.admin-fe-url}")
        private String adminFrontendUrl;

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                
                configuration.setAllowedOrigins(Arrays.asList(frontendUrl,
                                adminFrontendUrl));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("*")); // Cho phép tất cả headers
                configuration.setAllowCredentials(true); // Cho phép credentials

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }


}