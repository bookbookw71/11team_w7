package com.example.bookbookw71.dto;

import com.example.bookbookw71.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private int bookPage;

    @NotBlank
    private int star;

    @NotBlank
    @Pattern(regexp = "^\\d{4}\\/\\d{2}\\/\\d{2}$")
    private String readStart;

    @NotBlank
    @Pattern(regexp = "^([0-9]{4})\\/([0-9]{2})\\/([0-9]{2})$")
    private String readEnd;
}
