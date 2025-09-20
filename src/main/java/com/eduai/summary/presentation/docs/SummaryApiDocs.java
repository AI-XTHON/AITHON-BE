package com.eduai.summary.presentation.docs;

import com.eduai.common.dto.ApiResult;
import com.eduai.common.exception.ErrorResponse;
import com.eduai.summary.application.dto.SummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Summary", description = "요약 및 질문 생성 관련 API")
public interface SummaryApiDocs {

    @Operation(
            summary = "단일 요약 조회",
            description = "특정 요약의 상세 정보를 조회합니다.",
            security = @SecurityRequirement(name = "JWT Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "단일 요약 조회 성공",
                            content = @Content(schema = @Schema(implementation = SummaryResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "요약을 찾을 수 없음",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    ResponseEntity<ApiResult<SummaryResponse>> getRecentSummary(@Parameter(hidden = true) String email);

    @Operation(
            summary = "요약 리스트 조회",
            description = "사용자가 생성한 모든 요약의 리스트를 조회합니다.",
            security = @SecurityRequirement(name = "JWT Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요약 리스트 조회 성공",
                            content = @Content(schema = @Schema(implementation = SummaryResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "요약을 찾을 수 없음",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    ResponseEntity<ApiResult<List<SummaryResponse>>> getAllSummaries(@Parameter(hidden = true) String email);
}
