package com.example.bookbookw71.model;

public enum StatusEnum {

    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    INVALID_MEMBER(400,"INVALID_MEMBER"),
    MEMBER_NOT_FOUND(400, "사용자를 찾을 수 없습니다."),
    //PASSWORDS_NOT_MATCHED(400, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    TOKEN_DELETED(401, "Refresh-Token을 삭제했습니다."),
    LOGIN_REQUIRED(401,"로그인이 필요합니다."),
    INVALID_TOKEN(401, "Token이 유효하지 않습니다."),
    NOT_AUTHORITY(401, "작성자만 수정할 수 있습니다."),
    ID_DUPLICATION(400,"중복된 아이디입니다."),
    INVALID_URL(400,"url형식이 올바르지 않습니다"),
    SUCCESS(200,"성공");


    int statusCode;
    String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}

