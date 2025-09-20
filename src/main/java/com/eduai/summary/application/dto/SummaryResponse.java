package com.eduai.summary.application.dto;

import com.eduai.summary.domain.Summary;

public record SummaryResponse(

        String thumbNail,
        String summary,
        String oneLiner
) {
    public static SummaryResponse from(Summary summary) {
        return new SummaryResponse(
                summary.getResource().getFilePath(),
                summary.getSummary5().getFirst(),
                summary.getSlides().getFirst().getOneLiner()
        );
    }
}
