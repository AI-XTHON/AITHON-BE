package com.eduai.studysession.presentation.docs;

import com.eduai.common.dto.ApiResult;
import com.eduai.common.dto.PageResponse;
import com.eduai.common.exception.BusinessException;
import com.eduai.studysession.application.dto.SendMessageRequest;
import com.eduai.studysession.application.dto.StartSessionRequest;
import com.eduai.studysession.application.dto.SessionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "StudySession", description = "스터디 세션 (대화/학습 기록) 관련 API")
public interface StudySessionApiDocs {

    @Operation(
            summary = "새로운 스터디 세션 시작하기",
            description = "사용자의 첫 질문(PDF, 이미지, 텍스트)으로 새로운 스터디 세션을 생성합니다. 성공 시 생성된 세션 정보를 반환합니다.",
            security = @SecurityRequirement(name = "JWT Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "스터디 세션 생성 성공",
                            content = @Content(schema = @Schema(implementation = SessionResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = BusinessException.class))),
                    @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = BusinessException.class)))
            }
    )
    ResponseEntity<ApiResult<SessionResponse>> startSession(
            @Parameter(hidden = true)
            @AuthenticationPrincipal User user,

            @Parameter(description = "첫 질문 내용 및 정보 (JSON 형식)")
            @RequestPart("request") StartSessionRequest request,

            @Parameter(description = "질문에 포함할 이미지 파일 목록 (선택)")
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    );

    @Operation(
            summary = "기존 세션에 메시지 보내기",
            description = "진행 중인 스터디 세션에 새로운 메시지(질문)를 추가합니다.",
            security = @SecurityRequirement(name = "JWT Authentication"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "메시지 전송 성공", content = @Content(schema = @Schema(implementation = Long.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = BusinessException.class))),
                    @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = BusinessException.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 세션", content = @Content(schema = @Schema(implementation = BusinessException.class)))
            }
    )
    ResponseEntity<ApiResult<Long>> sendMessage(
            @Parameter(hidden = true)
            @AuthenticationPrincipal User user,

            @Parameter(description = "메시지를 보낼 세션의 ID", required = true)
            @PathVariable Long sessionId,

            @Parameter(description = "전송할 메시지 내용 (JSON 형식)")
            @RequestPart("request") SendMessageRequest request,

            @Parameter(description = "메시지에 포함할 이미지 파일 목록 (선택)")
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    );

    @Operation(
            summary = "세션 페이지네이션 조회하기",
            description = "사용자의 스터디 세션을 페이지 단위로 조회합니다.",
            security = @SecurityRequirement(name = "JWT Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "세션 페이지 조회 성공",
                            content = @Content(schema = @Schema(implementation = SessionResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = BusinessException.class))),
                    @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = BusinessException.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 세션", content = @Content(schema = @Schema(implementation = BusinessException.class)))
            }
    )
    ResponseEntity<ApiResult<PageResponse<SessionResponse>>> getSessionPage(
            @Parameter(hidden = true)
            @AuthenticationPrincipal User user,

            @Parameter(description = "페이지 정보 (page, size, sort)")
            @PageableDefault Pageable pageable
    );
}
