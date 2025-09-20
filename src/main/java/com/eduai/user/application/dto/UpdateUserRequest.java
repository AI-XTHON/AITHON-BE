package com.eduai.user.application.dto;

public record UpdateUserRequest(

        String job,
        String ageGroup,
        String purpose,
        String nickname
) {
}
