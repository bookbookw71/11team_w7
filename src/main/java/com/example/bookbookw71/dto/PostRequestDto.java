package com.example.bookbookw71.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private Long userId;
    private String username;
    private String title;
    private String content;
    private int bookPage;
    private String imageUrl;
    private int score;
    private String startTime;
    private String endTime;

}
