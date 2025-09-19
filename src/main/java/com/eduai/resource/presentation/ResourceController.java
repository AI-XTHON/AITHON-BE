package com.eduai.resource.presentation;

import com.eduai.auth.resolver.AuthUser;
import com.eduai.common.dto.ApiResult;
import com.eduai.resource.application.ResourceService;
import com.eduai.resource.application.dto.CreateResourceRequest;
import com.eduai.resource.presentation.docs.ResourceApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List; // List import 추가

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
public class ResourceController implements ResourceApiDocs {

    private final ResourceService resourceService;

    private static final List<String> ALLOWED_CONTENT_TYPES = List.of("application/pdf");

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<Long>> uploadResource(
            @AuthUser String email,
            @RequestPart("file") MultipartFile file,
            @RequestPart("request") CreateResourceRequest request
    ) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResult.error(HttpStatus.BAD_REQUEST, "파일이 비어 있습니다."));
            }

            String contentType = file.getContentType();
            if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
                return ResponseEntity.badRequest()
                        .body(ApiResult.error(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 형식입니다. (PDF만 가능)"));
            }

            Long resourceId = resourceService.uploadResource(file, email, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResult.success(HttpStatus.CREATED, "학습 자료가 성공적으로 업로드되었습니다.", resourceId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResult.error(HttpStatus.INTERNAL_SERVER_ERROR, "학습 자료 업로드 중 오류가 발생했습니다."));
        }
    }
}