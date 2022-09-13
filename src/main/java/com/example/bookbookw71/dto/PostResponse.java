package com.example.bookbookw71.dto;

import com.example.bookbookw71.model.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {
    private long id;

    private Member username;

    private String title;

    private String content;

    private int bookPage;

    private int score;

    private String startTime;

    private String endTime;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
