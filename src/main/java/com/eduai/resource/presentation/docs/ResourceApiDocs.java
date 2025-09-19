package com.eduai.resource.presentation.docs;

import com.eduai.common.dto.ApiResult;
import com.eduai.common.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Resource", description = "학습 자료 관련 API")
public interface ResourceApiDocs {

    @Operation(
            summary = "학습 자료 업로드",
            description = "PDF, TXT 등 새로운 학습 자료를 업로드합니다. 업로드 성공 시 생성된 리소스의 ID를 반환합니다.",
            security = @SecurityRequirement(name = "JWT Authentication"),
            requestBody = @RequestBody(
                    description = "업로드할 학습 자료 파일 (PDF, TXT 등)",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "학습 자료 업로드 성공",
                            content = @Content(schema = @Schema(implementation = Long.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 (파일이 없거나 지원하지 않는 형식)",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    ResponseEntity<ApiResult<Long>> uploadResource(
            @Parameter(hidden = true)
            String email,

            @Parameter(
                    description = "업로드할 파일",
                    required = true
            )
            MultipartFile file
    );
}