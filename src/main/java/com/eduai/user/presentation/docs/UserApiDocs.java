package com.eduai.user.presentation.docs;

import com.eduai.common.dto.ApiResult;
import com.eduai.common.exception.BusinessException;
import com.eduai.user.application.dto.UpdateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Member", description = "회원 관련 API")
public interface UserApiDocs {

    @Operation(
            summary = "회원 정보 수정",
            description = "현재 로그인된 사용자의 정보를 수정합니다.",
            security = @SecurityRequirement(name = "JWT Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 정보 수정 성공"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 (유효성 검증 실패)",
                            content = @Content(schema = @Schema(implementation = BusinessException.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = BusinessException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<Void>> updateMemberInfo(
            @Parameter(hidden = true)
            @AuthenticationPrincipal User user,

            @RequestBody UpdateUserRequest request
    );
}
