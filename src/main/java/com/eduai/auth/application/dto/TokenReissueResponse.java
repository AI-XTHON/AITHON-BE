package com.eduai.auth.application.dto;

public record TokenReissueResponse(

        String accessToken,
        String refreshToken
) {

    public static TokenReissueResponse of(String accessToken, String refreshToken) {
        return new TokenReissueResponse(accessToken, refreshToken);
    }
}
