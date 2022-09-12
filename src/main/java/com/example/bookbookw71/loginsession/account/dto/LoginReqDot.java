package com.example.bookbookw71.loginsession.account.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginReqDot {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}

