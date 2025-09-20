package com.eduai.summary.presentation;

import com.eduai.auth.resolver.AuthUser;
import com.eduai.common.dto.ApiResult;
import com.eduai.common.exception.BusinessException;
import com.eduai.common.exception.ErrorCode;
import com.eduai.summary.application.dto.SummaryResponse;
import com.eduai.summary.domain.Summary;
import com.eduai.summary.infrastructure.SummaryRepository;
import com.eduai.summary.presentation.docs.SummaryApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/summaries")
@RequiredArgsConstructor
public class SummaryController implements SummaryApiDocs {

    private final SummaryRepository summaryRepository;

    @GetMapping("/{resourceId}/recent")
    @Override
    public ResponseEntity<ApiResult<SummaryResponse>> getRecentSummary(@PathVariable Long resourceId) {
        Summary summary = summaryRepository.findSummaryByResourceId(resourceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        SummaryResponse response = SummaryResponse.from(summary);

        return ResponseEntity.ok(ApiResult.success(HttpStatus.OK, "요약 조회에 성공했습니다." , response));
    }
}
