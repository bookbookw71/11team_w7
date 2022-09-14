package com.example.bookbookw71.dto;

import com.example.bookbookw71.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePost {

    private String title;

    private String content;

    private String imageUrl;

    private int bookPage;

    private int score;

    private String startTime;

    private String endTime;

    private Member username;
}