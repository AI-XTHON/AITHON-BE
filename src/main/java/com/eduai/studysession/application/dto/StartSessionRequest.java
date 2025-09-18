package com.eduai.studysession.application.dto;

public record StartSessionRequest(

        Long resourceId,
        String content
) {
}
