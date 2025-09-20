package com.eduai.summary.application.dto;

import com.eduai.summary.domain.Summary;

public record SummaryResponse(

        String summary,
        String questionType
) {
    public static SummaryResponse from(Summary summary) {
        return new SummaryResponse(
                summary.getSummary5().getFirst(),
                summary.getSlides().getFirst().getOneLiner()
        );
    }
}
