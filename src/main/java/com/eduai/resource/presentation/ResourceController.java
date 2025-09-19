package com.eduai.resource.presentation;

import com.eduai.auth.resolver.AuthUser;
import com.eduai.common.dto.ApiResult;
import com.eduai.resource.application.ResourceService;
import com.eduai.resource.presentation.docs.ResourceApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
public class ResourceController implements ResourceApiDocs {

    private final ResourceService resourceService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<Long>> uploadResource(
            @AuthUser String email,
            @RequestPart("file") MultipartFile file
    ) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResult.error(HttpStatus.BAD_REQUEST, "파일이 비어 있습니다."));
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest()
                        .body(ApiResult.error(HttpStatus.BAD_REQUEST, "이미지 파일만 업로드 가능합니다."));
            }

            Long resourceId = resourceService.uploadResource(file, email);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResult.success(HttpStatus.CREATED, "학습 자료가 성공적으로 업로드되었습니다.", resourceId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResult.error(HttpStatus.INTERNAL_SERVER_ERROR, "학습 자료 업로드 중 오류가 발생했습니다."));
        }
    }
}
