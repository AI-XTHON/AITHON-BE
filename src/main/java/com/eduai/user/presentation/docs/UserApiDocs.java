package com.eduai.user.presentation.docs;

import com.eduai.common.dto.ApiResult;
import com.eduai.common.exception.BusinessException;
import com.eduai.user.application.dto.UpdateUserRequest;
import com.eduai.user.application.dto.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User", description = "회원 관련 API")
public interface UserApiDocs {

    @Operation(
            summary = "현재 사용자 정보 조회",
            description = "현재 로그인된 사용자의 이메일을 조회합니다.",
            security = @SecurityRequirement(name = "JWT Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "현재 사용자 정보 조회 성공"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = BusinessException.class))
                    )
            }
    )
    ResponseEntity<ApiResult<UserInfoResponse>> getMyInfo(@Parameter(hidden = true) String email);

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
    ResponseEntity<ApiResult<Void>> updateUserInfo(@Parameter(hidden = true) String email, UpdateUserRequest request);
}
