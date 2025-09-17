package com.eduai.common.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable) // TODO: CORS 설정 필요
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers(header ->
                        header
                                .contentSecurityPolicy(
                                        csp -> csp.policyDirectives(
                                                "default-src 'self'; img-src 'self' data:; script-src 'self'; style-src 'self' 'unsafe-inline'"
                                        )
                                )
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/health").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(
                        ex ->
                                ex.authenticationEntryPoint(
                                                (request, response, authException) ->
                                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                                        )
                                        .accessDeniedHandler(
                                                (request, response, accessDeniedException) ->
                                                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden")
                                        )
                )
        ;

        return http.build();
    }
}
