
package com.eduai.auth.application.dto;

public record TokenInfo(

        String grantType,
        String accessToken,
        String refreshToken
) {

    public static TokenInfo of(String accessToken, String refreshToken) {
        return new TokenInfo("Bearer", accessToken, refreshToken);
    }
}
