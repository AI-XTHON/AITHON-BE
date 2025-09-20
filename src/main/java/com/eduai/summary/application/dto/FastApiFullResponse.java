package com.eduai.summary.application.dto;

import com.eduai.quiz.domain.QuestionType;
import com.eduai.summary.domain.Summary;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// SummaryResponse.java
public record FastApiFullResponse(
        String filename,
        int pages,
        String model,
        ResultData result
) {
    public record ResultData(
            List<String> summary5,
            List<SlideDto> slides,
            List<GlossaryDto> glossary,
            QuestionDto questions,
            List<String> further
    ) {}

    public record SlideDto(String title, String one_liner, List<Integer> pages) {}
    public record GlossaryDto(String term, String definition, List<Integer> pages) {}
    public record QuestionDto(
            @JsonProperty("short")
            List<AnswerDto> shortAnswer,
            @JsonProperty("long")
            List<AnswerDto> longAnswer
    ) {}
    public record AnswerDto(String q, String a, List<Integer> pages) {}

    /**
     * Summary 엔티티를 SummaryResponse DTO로 변환하는 정적 팩토리 메서드
     * @param summary 조회된 Summary 엔티티
     * @return 클라이언트에 전달할 DTO
     */
    public static FastApiFullResponse from(Summary summary) {
        var slides = summary.getSlides().stream()
                .map(slide -> new SlideDto(slide.getTitle(), slide.getOneLiner(), slide.getPages()))
                .toList();

        var glossary = summary.getGlossaryTerms().stream()
                .map(term -> new GlossaryDto(term.getTerm(), term.getDefinition(), term.getPages()))
                .toList();

        var shortAnswers = summary.getQuestions().stream()
                .filter(q -> q.getType() == QuestionType.SHORT)
                .map(q -> new AnswerDto(q.getQ(), q.getA(), q.getPages()))
                .toList();

        var longAnswers = summary.getQuestions().stream()
                .filter(q -> q.getType() == QuestionType.LONG)
                .map(q -> new AnswerDto(q.getQ(), q.getA(), q.getPages()))
                .toList();

        var questions = new QuestionDto(shortAnswers, longAnswers);

        var result = new ResultData(
                summary.getSummary5(),
                slides,
                glossary,
                questions,
                summary.getFurtherTopics()
        );

        return new FastApiFullResponse(
                summary.getFilename(),
                summary.getPages(),
                summary.getModel(),
                result
        );
    }
}