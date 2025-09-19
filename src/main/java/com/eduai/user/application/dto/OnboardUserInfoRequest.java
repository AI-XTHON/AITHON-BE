package com.eduai.user.application.dto;

public record OnboardUserInfoRequest(

        String job,
        String ageGroup,
        String purpose
) {
}
