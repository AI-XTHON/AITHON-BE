package com.eduai.quiz.presentation;

import com.eduai.auth.resolver.AuthUser;
import com.eduai.common.dto.ApiResult;
import com.eduai.common.exception.BusinessException;
import com.eduai.common.exception.ErrorCode;
import com.eduai.quiz.application.dto.GetQuizzesDto;
import com.eduai.quiz.infrastructure.QuestionRepository;
import com.eduai.quiz.presentation.docs.QuestionApiDocs;
import com.eduai.summary.domain.Summary;
import com.eduai.summary.infrastructure.SummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quizzes")
@RequiredArgsConstructor
public class QuestionController implements QuestionApiDocs {

    private final QuestionRepository questionRepository;
    private final SummaryRepository summaryRepository;

    @GetMapping
    @Override
    public ResponseEntity<ApiResult<List<GetQuizzesDto>>> getQuizzes(@AuthUser String email) {

        Summary summary = summaryRepository.getByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        List<GetQuizzesDto> response = questionRepository.findAllBySummary(summary)
                .stream().map(GetQuizzesDto::from).toList();

        return ResponseEntity.ok(ApiResult.success(HttpStatus.OK, "퀴즈 조회에 성공했습니다.", response));
    }
}
