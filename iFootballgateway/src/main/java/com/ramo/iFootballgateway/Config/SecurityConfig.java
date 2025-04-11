package com.ramo.iFootballgateway.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import reactor.core.publisher.Mono;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/blogs").permitAll()
                        .pathMatchers("/api/fixtures/tournaments/search-by-team").permitAll()
                        .pathMatchers("/api/fixtures/group-by-date").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer ->
                                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }



    @Bean
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        logger.info("Initializing ReactiveJwtDecoder with secret key: {}", secretKey);

        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");

        return NimbusReactiveJwtDecoder.withSecretKey(secretKeySpec).build();
    }

}
