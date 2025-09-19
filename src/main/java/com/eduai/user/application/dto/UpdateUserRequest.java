package com.eduai.user.application.dto;

public record UpdateUserRequest(

        String job,
        String ageRange,
        String purpose,
        String nickname
) {

}
