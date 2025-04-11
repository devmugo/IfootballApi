package com.ramo.iFootballgateway.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

    @Configuration
    public class CorsConfig {

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:4200"));  // Allow frontend origin
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Allowed methods
            config.setAllowedHeaders(List.of("*"));  // Allow all headers
            config.setExposedHeaders(List.of("Authorization", "Content-Type"));
            config.setAllowCredentials(true);  // Allow credentials (cookies, authentication headers)
            config.setMaxAge(3600L);  // Cache preflight response for 1 hour

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);  // Apply CORS to all endpoints
            return source;
        }
    }


