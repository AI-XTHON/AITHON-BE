package com.eduai.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Token 관련 에러 코드
    TOKEN_NOT_FOUND("토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED.value()),

    // 인증 관련 에러 코드
    UNAUTHORIZED("권한이 없는 요청입니다.", HttpStatus.UNAUTHORIZED.value()),
    FORBIDDEN("접근 권한이 없습니다.", HttpStatus.FORBIDDEN.value()),

    // 사용자 관련 에러 코드
    BUSINESS_ERROR("비즈니스 로직 오류입니다.", HttpStatus.BAD_REQUEST.value()),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND.value()),

    // 4xx 에러 코드
    BAD_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST.value()),
    INVALID_INPUT_VALUE("잘못된 입력입니다.", HttpStatus.BAD_REQUEST.value()),
    METHOD_NOT_ALLOWED("허용되지 않은 HTTP 메소드입니다.", HttpStatus.METHOD_NOT_ALLOWED.value()),
    NOT_ACCEPTABLE("요청한 응답 미디어 타입을 지원하지 않습니다.", HttpStatus.NOT_ACCEPTABLE.value()),
    UNSUPPORTED_MEDIA_TYPE("지원하지 않는 미디어 타입입니다.", HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()),

    // 5xx 에러 코드
    INTERNAL_SERVER_ERROR("예상치 못한 서버 오류입니다. 관리자에게 문의해주세요.", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    NOT_IMPLEMENTED("요청하신 기능은 아직 구현되지 않았습니다.", HttpStatus.NOT_IMPLEMENTED.value());

    private final String message;
    private final int status;
}
