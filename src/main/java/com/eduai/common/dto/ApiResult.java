package com.eduai.common.dto;

import com.eduai.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

    private final int code;
    private final String status;
    private final String message;
    private final T data;

    private ApiResult(HttpStatus httpStatus, String message, T data) {
        this.code = httpStatus.value();
        this.status = "SUCCESS";
        this.message = message;
        this.data = data;
    }

    private ApiResult(HttpStatus httpStatus, String status, String message) {
        this.code = httpStatus.value();
        this.status = status;
        this.message = message;
        this.data = null;
    }

    private ApiResult(ErrorCode errorCode) {
        this.code = errorCode.getStatus();
        this.status = "FAIL";
        this.message = errorCode.getMessage();
        this.data = null;
    }

    public static <T> ApiResult<T> fail(ErrorCode errorCode) {
        return new ApiResult<>(errorCode);
    }

    public static <T> ApiResult<T> success(HttpStatus httpStatus, String message, T data) {
        return new ApiResult<>(httpStatus, message, data);
    }

    public static <T> ApiResult<T> success(HttpStatus httpStatus, String message) {
        return new ApiResult<>(httpStatus, message, null);
    }

    public static <T> ApiResult<T> fail(HttpStatus httpStatus, String message) {
        return new ApiResult<>(httpStatus, "FAIL", message);
    }

    public static <T> ApiResult<T> error(HttpStatus httpStatus, String message) {
        return new ApiResult<>(httpStatus, "ERROR", message);
    }
}
