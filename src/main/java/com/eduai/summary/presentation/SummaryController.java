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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/summaries")
@RequiredArgsConstructor
public class SummaryController implements SummaryApiDocs {

    private final SummaryRepository summaryRepository;

    @GetMapping("/recent")
    @Override
    public ResponseEntity<ApiResult<SummaryResponse>> getRecentSummary(@AuthUser String email) {
        Summary summary = summaryRepository.getByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        SummaryResponse response = SummaryResponse.from(summary);

        return ResponseEntity.ok(ApiResult.success(HttpStatus.OK, "요약 조회에 성공했습니다." , response));
    }

    @GetMapping
    @Override
    public ResponseEntity<ApiResult<List<SummaryResponse>>> getAllSummaries(@AuthUser String email) {
        List<Summary> summaries = summaryRepository.getAllByEmail(email);

        if (summaries.isEmpty()) {
            throw new BusinessException(ErrorCode.SUMMARY_NOT_FOUND);
        }

        List<SummaryResponse> responseList = summaries.stream()
                .map(SummaryResponse::from)
                .toList();

        return ResponseEntity.ok(ApiResult.success(HttpStatus.OK, "요약 리스트 조회에 성공했습니다." , responseList));
    }

}
