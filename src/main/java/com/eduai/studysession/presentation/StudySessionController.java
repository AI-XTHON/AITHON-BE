package com.eduai.studysession.presentation;

import com.eduai.common.dto.ApiResult;
import com.eduai.common.dto.PageResponse;
import com.eduai.studysession.application.dto.SendMessageRequest;
import com.eduai.studysession.application.dto.SessionResponse;
import com.eduai.studysession.application.dto.StartSessionRequest;
import com.eduai.studysession.presentation.docs.StudySessionApiDocs;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/study-sessions")
public class StudySessionController implements StudySessionApiDocs {

    @PostMapping
    @Override
    public ResponseEntity<ApiResult<SessionResponse>> startSession(User user, StartSessionRequest request, List<MultipartFile> images) {
        return null;
    }

    @PostMapping("/{sessionId}/messages")
    @Override
    public ResponseEntity<ApiResult<Long>> sendMessage(User user, Long sessionId, SendMessageRequest request, List<MultipartFile> images) {
        return null;
    }

    @GetMapping
    @Override
    public ResponseEntity<ApiResult<PageResponse<SessionResponse>>> getSessionPage(User user, Pageable pageable) {
        return null;
    }
}
