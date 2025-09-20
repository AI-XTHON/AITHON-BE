
package com.eduai.auth.handler;

import com.eduai.auth.application.dto.TokenInfo;
import com.eduai.auth.jwt.JwtTokenProvider;
import com.eduai.user.infrastructure.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Value("${cors.allowed-origins}")
    private String allowedOrigin;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login successful!");

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        log.info("Generated Token Info: {}", tokenInfo.accessToken());

        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    user.updateRefreshToken(tokenInfo.refreshToken());
                    userRepository.save(user);
                    log.info("Refresh Token for user '{}' updated successfully.", user.getEmail());
                });

        String targetUrl = UriComponentsBuilder.fromUriString(allowedOrigin + "/login/success")
                .queryParam("accessToken", tokenInfo.accessToken())
                .queryParam("refreshToken", tokenInfo.refreshToken())
                .build().toUriString();

        response.sendRedirect(targetUrl);
    }
}
