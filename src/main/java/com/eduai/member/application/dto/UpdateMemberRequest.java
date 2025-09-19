package com.eduai.member.application.dto;

public record UpdateMemberRequest(

        String job,
        String ageRange,
        String purpose,
        String nickname
) {

}
