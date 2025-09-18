package com.eduai.resource.presentation;

import com.eduai.common.dto.ApiResult;
import com.eduai.resource.presentation.docs.ResourceApiDocs;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resources")
public class ResourceController implements ResourceApiDocs {

    @PostMapping
    @Override
    public ResponseEntity<ApiResult<Long>> uploadResource(User user, MultipartFile file) {
        return null;
    }
}
