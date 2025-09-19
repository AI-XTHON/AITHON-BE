package com.eduai.auth.presentation.docs;

import com.eduai.auth.application.dto.TokenReissueResponse;
import com.eduai.common.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication", description = "인증 관련 API")
public interface AuthApiDocs {

    @Operation(
            summary = "토큰 갱신",
            description = "리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급받습니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "토큰 갱신 성공"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패 (유효하지 않은 리프레시 토큰)"
                    )
            }
    )
    ResponseEntity<ApiResult<TokenReissueResponse>> reissueToken(String refreshToken);
}
