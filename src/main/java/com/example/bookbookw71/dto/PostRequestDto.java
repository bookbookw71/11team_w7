package com.example.bookbookw71.dto;

import com.example.bookbookw71.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostRequestDto {
    private long id;

    private String title;

    private String content;

    private String imageUrl;

    private int bookPage;

    private int score;

    private String readStart;

    private String readEnd;
}
