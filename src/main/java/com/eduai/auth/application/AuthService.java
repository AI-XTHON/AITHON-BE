package com.eduai.auth.application;

import com.eduai.auth.application.dto.TokenInfo;
import com.eduai.auth.application.dto.TokenReissueResponse;
import com.eduai.auth.jwt.JwtTokenProvider;
import com.eduai.user.domain.User;
import com.eduai.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenReissueResponse reissueTokens(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token 입니다.");
        }

        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Refresh Token에 해당하는 사용자를 찾을 수 없습니다."));


        String newAccessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole());
        String newRefreshToken = jwtTokenProvider.createRefreshToken();

        user.updateRefreshToken(newRefreshToken);

        return TokenReissueResponse.of(newAccessToken, newRefreshToken);
    }
}
