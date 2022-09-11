package com.example.bookbookw71.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private int bookPage;
    private Long userId;
}
