package com.example.bookbookw71.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignDto {

    private String email;

    @NotBlank
    @Size(min=4,max = 12)
    private String username;

    @Size(min=4,max = 20)
    private String password;

}
