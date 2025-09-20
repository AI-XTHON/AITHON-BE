package com.eduai.quiz.application.dto;

import com.eduai.quiz.domain.Question;

public record GetQuizzesDto(
        String quiz,
        String answer
) {
    public static GetQuizzesDto from(Question question) {
        return new GetQuizzesDto(
                question.getQ(),
                question.getA()
        );
    }
}
