package com.example.bookbookw71.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private Error error;


    public ResponseDto(boolean success, T data, Object error) {
    }

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<T>(true, data, null);
    }
    public static <T> ResponseDto<T> fail(String code, String message) {
        return new ResponseDto<T>(false, null, new Error(code, message));
    }

    @Getter
    @AllArgsConstructor
    static class Error {
        private String code;
        private String message;
    }

}
