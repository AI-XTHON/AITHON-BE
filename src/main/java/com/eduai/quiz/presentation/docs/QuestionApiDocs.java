package com.eduai.quiz.presentation.docs;

import com.eduai.auth.resolver.AuthUser;
import com.eduai.common.dto.ApiResult;
import com.eduai.common.exception.ErrorResponse;
import com.eduai.quiz.application.dto.GetQuizzesDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Question", description = "퀴즈 조회 관련 API")
public interface QuestionApiDocs {

    @Operation(
            summary = "퀴즈 조회",
            description = "특정 요약에 대한 퀴즈 리스트를 조회합니다.",
            security = @SecurityRequirement(name = "JWT Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "퀴즈 조회 성공",
                            content = @Content(schema = @Schema(implementation = GetQuizzesDto.class))
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
    ResponseEntity<ApiResult<List<GetQuizzesDto>>> getQuizzes(@Parameter(hidden = true) String email);
}
