package com.eduai.common.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health Check", description = "API 상태 확인을 위한 헬스 체크 엔드포인트")
@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
