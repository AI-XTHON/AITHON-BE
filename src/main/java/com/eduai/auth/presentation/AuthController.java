package com.eduai.auth.presentation;

import com.eduai.auth.application.AuthService;
import com.eduai.auth.application.dto.TokenReissueResponse;
import com.eduai.auth.presentation.docs.AuthApiDocs;
import com.eduai.common.dto.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApiDocs {

    private final AuthService authService;

    @PostMapping("/reissue")
    @Override
    public ResponseEntity<ApiResult<TokenReissueResponse>> reissueToken(@RequestHeader("X-Refresh-Token") String refreshToken) {
        TokenReissueResponse response = authService.reissueTokens(refreshToken);
        return ResponseEntity.ok(ApiResult.success(HttpStatus.OK, "토큰이 성공적으로 재발급되었습니다.", response));
    }
}
