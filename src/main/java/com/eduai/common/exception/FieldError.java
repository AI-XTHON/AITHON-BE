package com.eduai.common.exception;

public record FieldError(

        String field,
        String reason
) {

    public static FieldError of(org.springframework.validation.FieldError fieldError) {
        return new FieldError(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
