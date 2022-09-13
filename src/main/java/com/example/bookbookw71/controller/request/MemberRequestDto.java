package com.example.bookbookw71.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4, max = 12)
    private String username;

    @NotBlank
    @Size(min = 4, max = 32)
    private String password;

//    @NotBlank
//    private String pwdCheck;

}
